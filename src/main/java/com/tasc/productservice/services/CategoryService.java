package com.tasc.productservice.services;

import com.tasc.productservice.database.entity.Category;
import com.tasc.productservice.database.entity.CategoryRelationship;
import com.tasc.productservice.database.repository.CategoryRelationshipRepository;
import com.tasc.productservice.database.repository.CategoryRepository;
import com.tasc.productservice.database.specification.CategorySpecification;
import com.tasc.productservice.database.specification.SearchBody;
import com.tasc.productservice.database.specification.SpecSearchCriteria;
import com.tasc.productservice.models.ApiException;
import com.tasc.productservice.models.enums.ERROR;
import com.tasc.productservice.models.request.CategoryRequest;
import com.tasc.productservice.models.response.BaseResponse;
import com.tasc.productservice.utils.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;

import static com.tasc.productservice.database.specification.SearchCriteriaOperator.LIKE;

@Service
@Log4j2
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryRelationshipRepository categoryRelationshipRepository;

    @Transactional
    public BaseResponse findAllCategory(SearchBody searchBody) throws ApiException {

        Specification specification = Specification.where(null);

        if (!StringUtils.isBlank(searchBody.getName())) {
            specification = specification.and(new CategorySpecification(
                    new SpecSearchCriteria("name", LIKE, "%" + searchBody.getName() + "%"))
            );
        }

        if (!StringUtils.isBlank(searchBody.getDescription())) {
            specification = specification.and(new CategorySpecification(
                    new SpecSearchCriteria("description", LIKE, "%" + searchBody.getDescription() + "%"))
            );
        }

        Sort sort = Sort.by(Sort.Order.asc("id"));

        if (!StringUtils.isBlank(searchBody.getSort())) {
            if (searchBody.getSort().contains("desc")) {
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }

        Pageable pageable = PageRequest.of(searchBody.getPage() - 1, searchBody.getLimit(), sort);
        Page<Category> categories = categoryRepository.findAll(specification, pageable);
        List<Category> sortedCategories = categories.getContent();

        Map<String, Object> responses = new HashMap<>();

        responses.put("content", sortedCategories);
        responses.put("currentPage", categories.getNumber() + 1);
        responses.put("totalItems", categories.getTotalElements());
        responses.put("totalPage", categories.getTotalPages());

        return new BaseResponse<>(responses);
    }

    @Transactional
    public BaseResponse findCategoryById(Long id) throws ApiException {
        return new BaseResponse<>(categoryRepository.findById(id));
    }

    @Transactional
    public BaseResponse createCategory(CategoryRequest request) throws ApiException {

        validateRequestCreateException(request);

        if (!request.checkIsRoot()) {
            Optional<Category> checkParentOpt = categoryRepository.findById(request.getParentId());

            if (checkParentOpt.isEmpty()) {
                log.info("parent is invalid");
                throw new ApiException(ERROR.INVALID_PARAM, "parent is invalid");
            }
        }

        Category category = Category.builder()
                .name(request.getName())
                .icon(request.getIcon())
                .description(request.getDescription())
                .isRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF)
                .build();

        categoryRepository.save(category);

        if (!request.checkIsRoot()) {
            // create relationship
            CategoryRelationship categoryRelationship = new CategoryRelationship();
            CategoryRelationship.Pk pk = new CategoryRelationship.Pk(request.getParentId(), category.getId());
            categoryRelationship.setPk(pk);
            categoryRelationshipRepository.save(categoryRelationship);
        }

        log.info("SUCCESS");
        return new BaseResponse<>(category);
    }

    @Transactional
    public BaseResponse editCategory(Long id, CategoryRequest request) throws ApiException {

        log.info("edit category with id: {} , json body: {} ", id, request);
        validateRequestCreateException(request);

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = Category.builder()
                    .name(optionalCategory.get().getName())
                    .description(optionalCategory.get().getDescription())
                    .icon(optionalCategory.get().getIcon())
                    .isRoot(optionalCategory.get().getIsRoot())
                    .build();
            categoryRepository.save(category);
            return new BaseResponse<>(category);
        }
        throw new ApiException(ERROR.INVALID_PARAM, "Category not found");
    }

    @Transactional
    public BaseResponse deleteCategory(Long id) throws ApiException {
        categoryRepository.deleteById(id);

        this.deleteCategoryImpl(id);
        return new BaseResponse<>();
    }

    private void validateRequestCreateException(CategoryRequest request) throws ApiException {
        if (StringUtils.isBlank(request.getIcon())) {
            log.info("icon is blank");
            throw new ApiException(ERROR.INVALID_PARAM, "Icon is blank");
        }

        if (StringUtils.isBlank(request.getName())) {
            log.info("name is blank");
            throw new ApiException(ERROR.INVALID_PARAM, "Name is blank");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            log.info("description is Blank");
            throw new ApiException(ERROR.INVALID_PARAM, "Description is Blank");
        }

        if (request.checkIsRoot() && request.getParentId() != null) {
            log.info("level is invalid");
            throw new ApiException(ERROR.INVALID_PARAM, "Level is invalid");
        }

        if (!request.checkIsRoot() && request.getParentId() == null) {
            log.info("parent is blank");
            throw new ApiException(ERROR.INVALID_PARAM, "Parent is blank");
        }
    }

    private void deleteCategoryImpl(Long id) throws ApiException {
        List<CategoryRelationship> listChildren = categoryRelationshipRepository.findAllChildrenByParentId(id);

        if (CollectionUtils.isEmpty(listChildren))
            return;

        List<CategoryRelationship> deleteRelationship = new ArrayList<>();
        for (CategoryRelationship cr : listChildren) {

            long countParent = categoryRelationshipRepository.countParent(cr.getPk().getChildId());

            if (countParent == 1) {
                deleteRelationship.add(cr);
                this.deleteCategoryImpl(cr.getPk().getChildId());
            }
        }

        if (!CollectionUtils.isEmpty(deleteRelationship)) {
            categoryRelationshipRepository.deleteAll(deleteRelationship);
        }
    }
}

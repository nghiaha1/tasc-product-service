package com.tasc.productservice.services;

import com.tasc.productservice.database.entity.Category;
import com.tasc.productservice.database.repository.CategoryRepository;
import com.tasc.productservice.database.specification.CategorySpecification;
import com.tasc.productservice.database.specification.SearchBody;
import com.tasc.productservice.database.specification.SpecSearchCriteria;
import com.tasc.productservice.models.ApiException;
import com.tasc.productservice.models.enums.ERROR;
import com.tasc.productservice.models.request.CategoryRequest;
import com.tasc.productservice.models.response.BaseResponse;
import com.tasc.productservice.models.response.SearchCategoryResponse;
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

import javax.transaction.Transactional;
import java.util.*;

import static com.tasc.productservice.database.specification.SearchCriteriaOperator.LIKE;

@Service
@Log4j2
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public BaseResponse findAllCategory(SearchBody searchBody) throws ApiException {

        Specification specification = Specification.where(null);

        if (StringUtils.isNotBlank(searchBody.getName())) {
            specification = specification.and(new CategorySpecification(
                    new SpecSearchCriteria("name", LIKE, "%" + searchBody.getName() + "%"))
            );
        }

        if (StringUtils.isNotBlank(searchBody.getDescription())) {
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

        Pageable pageable = PageRequest.of(searchBody.getPage() - 1, searchBody.getPageSize(), sort);
        Page<Category> categories = categoryRepository.findAll(specification, pageable);
        List<Category> sortedCategories = categories.getContent();

        Map<String, Object> responses = new HashMap<>();

        responses.put("content", sortedCategories);
        responses.put("currentPage", categories.getNumber() + 1);
        responses.put("totalItems", categories.getTotalElements());
        responses.put("totalPage", categories.getTotalPages());

        return new BaseResponse<>(responses);
    }

    public BaseResponse findParentByChild_1(long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Category not exists");
        }

        return new BaseResponse<>(categoryRepository.findParentByChild(id));
    }

    public BaseResponse findChildByParent_1(long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Category not exists");
        }

        return new BaseResponse<>(categoryRepository.findChildByParent(id));
    }

    @Transactional
    public BaseResponse findParentByChild_2(SearchBody searchBody) {

        if (searchBody.getPage() < 1) {
            searchBody.setPage(1);
        }
        if (searchBody.getPageSize() < 1) {
            searchBody.setPageSize(1);
        }

        SearchCategoryResponse.Data data = new SearchCategoryResponse.Data();
        data.setCurrentPage(searchBody.getPage());
        data.setPageSize(searchBody.getPageSize());

        SearchCategoryResponse response = new SearchCategoryResponse();
        response.setData(data);

        return new BaseResponse(categoryRepository.findParentByChild(searchBody, data));
    }

    @Transactional
    public BaseResponse findChildByParent_2(SearchBody searchBody) {

        if (searchBody.getPage() < 1) {
            searchBody.setPage(1);
        }
        if (searchBody.getPageSize() < 1) {
            searchBody.setPageSize(1);
        }

        SearchCategoryResponse.Data data = new SearchCategoryResponse.Data();
        data.setCurrentPage(searchBody.getPage());
        data.setPageSize(searchBody.getPageSize());

        SearchCategoryResponse response = new SearchCategoryResponse();
        response.setData(data);

        return new BaseResponse(categoryRepository.findChildByParent(searchBody, data));
    }

    @Transactional
    public SearchCategoryResponse findAllWithView(SearchBody searchBody) {

        if (searchBody.getPage() < 1) {
            searchBody.setPage(1);
        }
        if (searchBody.getPageSize() < 1) {
            searchBody.setPageSize(1);
        }

        SearchCategoryResponse.Data data = new SearchCategoryResponse.Data();
        data.setCurrentPage(searchBody.getPage());
        data.setPageSize(searchBody.getPageSize());

        categoryRepository.findAllUsingView(searchBody, data);

        SearchCategoryResponse response = new SearchCategoryResponse();
        response.setData(data);

        return response;
    }

    @Transactional
    public SearchCategoryResponse findAll(SearchBody searchBody) {

        if (searchBody.getPage() < 1) {
            searchBody.setPage(1);
        }
        if (searchBody.getPageSize() < 1) {
            searchBody.setPageSize(1);
        }

        SearchCategoryResponse.Data data = new SearchCategoryResponse.Data();
        data.setCurrentPage(searchBody.getPage());
        data.setPageSize(searchBody.getPageSize());

        categoryRepository.findAll(searchBody, data);

        SearchCategoryResponse response = new SearchCategoryResponse();
        response.setData(data);

        return response;
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

            Category parent = checkParentOpt.get();
            Set<Category> setParent = new HashSet<>();
            setParent.add(parent);

            Category category = Category.builder()
                    .name(request.getName())
                    .icon(request.getIcon())
                    .description(request.getDescription())
                    .isRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF)
                    .parent(setParent)
                    .subCategories(new HashSet<>())
                    .build();

            categoryRepository.save(category);

            return new BaseResponse<>(category);
        }


        Category category = Category.builder()
                .name(request.getName())
                .icon(request.getIcon())
                .description(request.getDescription())
                .subCategories(new HashSet<>())
                .parent(null)
                .isRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF)
                .build();

        categoryRepository.save(category);

        log.info("SUCCESS");
        return new BaseResponse<>(category);
    }

    @Transactional
    public BaseResponse editCategory(Long id, CategoryRequest request) throws ApiException {

        log.info("edit category with id: {} , json body: {} ", id, request);
        validateRequestCreateException(request);

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (!optionalCategory.isPresent()) {
            log.debug("Not found category with id {} on database", id);
            throw new ApiException(ERROR.INVALID_PARAM, "Category not found");
        }

        Category category = optionalCategory.get();

        if (request.getIsRoot() != null && category.getIsRoot() != request.getIsRoot()) {
            log.debug("Request change category type from {} to type {}", category.getIsRoot(), request.getIsRoot());
            throw new ApiException(ERROR.INVALID_PARAM);
        }
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        category.setName(request.getName());

        log.info("Edit category with id {} success", id);
        categoryRepository.save(category);
        return new BaseResponse<>(category);
    }

    @Transactional
    public BaseResponse deleteCategory(Long id) throws ApiException {

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (!optionalCategory.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Category not found");
        }

        if (optionalCategory.get().getIsRoot() == 0) {
            for (Category category : optionalCategory.get().getParent()) {
                category.getSubCategories().remove(optionalCategory.get());
            }
        }

        Set<Category> subCategories;

        for (Category category : optionalCategory.get().getSubCategories()) {
            if (category.getParent().size() == 1) {
                this.deleteCategory(category.getId());
            } else
                category.getParent().remove(optionalCategory.get());
        }

        categoryRepository.deleteById(id);

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

}

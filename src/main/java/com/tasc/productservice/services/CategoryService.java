package com.tasc.productservice.services;

import com.tasc.productservice.database.entity.Category;
import com.tasc.productservice.database.repository.CategoryRepository;
import com.tasc.productservice.models.ApiException;
import com.tasc.productservice.models.enums.ERROR;
import com.tasc.productservice.models.request.CategoryRequest;
import com.tasc.productservice.models.response.BaseResponse;
import com.tasc.productservice.utils.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Log4j2
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

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

        log.info("SUCCESS");
        return new BaseResponse(category);
    }

    private void validateRequestCreateException(CategoryRequest request) throws ApiException {
        if (StringUtils.isBlank(request.getIcon())) {
            log.info("icon is blank");
            throw new ApiException(ERROR.INVALID_PARAM, "icon is blank");
        }

        if (StringUtils.isBlank(request.getName())) {
            log.info("name is blank");
            throw new ApiException(ERROR.INVALID_PARAM, "name is blank");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            log.info("description is Blank");
            throw new ApiException(ERROR.INVALID_PARAM, "description is Blank");
        }

        if (request.checkIsRoot() && request.getParentId() != null) {
            log.info("level is invalid");
            throw new ApiException(ERROR.INVALID_PARAM, "level is invalid");
        }

        if (!request.checkIsRoot() && request.getParentId() == null) {
            log.info("parent is blank");
            throw new ApiException(ERROR.INVALID_PARAM, "parent is blank");
        }
    }
}

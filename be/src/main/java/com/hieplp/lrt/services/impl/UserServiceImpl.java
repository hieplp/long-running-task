package com.hieplp.lrt.services.impl;

import com.hieplp.lrt.common.constant.CodeResponse;
import com.hieplp.lrt.common.constant.StaticEnum;
import com.hieplp.lrt.common.exception.CommonException;
import com.hieplp.lrt.common.exception.DataException;
import com.hieplp.lrt.common.handler.redis.IRedisHandler;
import com.hieplp.lrt.common.pojo.payload.request.CommonRequest;
import com.hieplp.lrt.common.pojo.payload.response.CommonResponse;
import com.hieplp.lrt.common.util.JsonConverter;
import com.hieplp.lrt.common.util.ValidationTool;
import com.hieplp.lrt.factory.imp.IImportFactory;
import com.hieplp.lrt.factory.imp.ImportFactory;
import com.hieplp.lrt.payload.request.user.ImportUsersRequest;
import com.hieplp.lrt.payload.request.user.ImportedUser;
import com.hieplp.lrt.services.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class UserServiceImpl implements IUserService {

    private final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final IImportFactory importFactory;
    private final IRedisHandler redisHandler;

    @Inject
    public UserServiceImpl(ImportFactory importFactory,
                           IRedisHandler redisHandler) {
        this.importFactory = importFactory;
        this.redisHandler = redisHandler;
    }

    @Override
    public CommonResponse importUsers(CommonRequest commonRequest) {
        try {
            LOGGER.info("Import users with request: {}", JsonConverter.toJson(commonRequest));

            ImportUsersRequest request = JsonConverter.fromJson(commonRequest.getData(), ImportUsersRequest.class);
            ValidationTool.checkNotNull(request.getRefKey(), request.getUsers());

            importFactory.getImport(StaticEnum.ImportType.USER)
                    .setRefKey(request.getRefKey())
                    .setImportData(request.getUsers())
                    // .setUserId(commonRequest.getHeaders().getUserId())
                    .setUserId("system")
                    .validate()
                    .importData();

            return CommonResponse.builder()
                    .response(CodeResponse.SuccessCode.SUCCESS)
                    .build();
        } catch (CommonException.ValidationException e) {
            LOGGER.error("Validation error: {}", e.getMessage());
            return new CommonResponse(CodeResponse.ClientErrorCode.BAD_REQUEST);
        } catch (DataException.DuplicatedPhoneException e) {
            LOGGER.error("Duplicated phone error: {}", e.getMessage());
            LOGGER.debug("Duplicated phone list: {}", JsonConverter.toJson(e.getPhones()));
            return CommonResponse.builder()
                    .response(CodeResponse.ClientErrorCode.DUPLICATED_PHONE)
                    .data(e.getPhones())
                    .build();
        } catch (DataException.DuplicatedPersonalIdException e) {
            LOGGER.error("Duplicated personalId error: {}", e.getMessage());
            LOGGER.debug("Duplicated personalId list: {}", JsonConverter.toJson(e.getPersonalIds()));
            return CommonResponse.builder()
                    .response(CodeResponse.ClientErrorCode.DUPLICATED_PERSONAL_ID)
                    .data(e.getPersonalIds())
                    .build();
        } catch (Exception e) {
            LOGGER.error("Unknown error when import users: {}", e.getMessage());
            return new CommonResponse(CodeResponse.ServerErrorCode.INTERNAL_SERVER);
        }
    }

    @Override
    public CommonResponse getImportUsersData(CommonRequest commonRequest) {
        try {
            LOGGER.info("Get imported users data with request: {}", commonRequest.getData());
            ImportUsersRequest request = JsonConverter.fromJson(commonRequest.getData(), ImportUsersRequest.class);
            LOGGER.info("Get imported users data with refKey: {}", request.getRefKey());
            String data = redisHandler.get(request.getRefKey());
            List<ImportedUser> response = JsonConverter.fromJsonToList(data, ImportedUser[].class);
            return CommonResponse.builder()
                    .response(CodeResponse.SuccessCode.SUCCESS)
                    .data(response)
                    .build();
        } catch (DataException.NotFoundException e) {
            LOGGER.error("Not found error: {}", e.getMessage());
            return new CommonResponse(CodeResponse.ClientErrorCode.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error("Unknown error when get imported users data: {}", e.getMessage());
            return new CommonResponse(CodeResponse.ServerErrorCode.INTERNAL_SERVER);
        }
    }
}

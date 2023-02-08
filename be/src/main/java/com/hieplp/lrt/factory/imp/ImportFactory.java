package com.hieplp.lrt.factory.imp;

import com.hieplp.lrt.common.constant.StaticEnum;
import com.hieplp.lrt.common.exception.CommonException;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;

public class ImportFactory implements IImportFactory {

    @Inject
    private Map<StaticEnum.ImportType, Provider<Import>> mapBinder;

    public Import getImport(StaticEnum.ImportType type) {
        switch (type) {
            case USER:
            case ROLE:
                return mapBinder.get(type).get();
            default:
                throw new CommonException.ValidationException("Import type not supported");
        }
    }
}

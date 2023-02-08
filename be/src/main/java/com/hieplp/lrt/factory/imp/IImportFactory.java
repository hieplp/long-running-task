package com.hieplp.lrt.factory.imp;

import com.hieplp.lrt.common.constant.StaticEnum;

public interface IImportFactory {
    Import getImport(StaticEnum.ImportType type);
}

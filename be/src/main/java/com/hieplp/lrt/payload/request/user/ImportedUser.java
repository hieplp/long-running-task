package com.hieplp.lrt.payload.request.user;

import com.hieplp.lrt.common.annotation.Length;
import com.hieplp.lrt.common.annotation.MaxLength;
import com.hieplp.lrt.common.annotation.MinLength;
import com.hieplp.lrt.common.annotation.Phone;
import com.hieplp.lrt.common.constant.StaticEnum;
import lombok.Data;

@Data
public class ImportedUser {
    private Integer id;
    @MinLength(length = 1, name = "name")
    @MaxLength(length = 50, name = "name")
    private String name;
    @Length(length = 10, name = "personalId")
    private String personalId;
    @Length(length = 10, name = "phone")
    @Phone
    private String phone;
    private byte status = StaticEnum.Status.IN_PROGRESS.getByteStatus();
//    private boolean isNameValid = true;
//    private boolean isPersonalIdValid = true;
//    private boolean isPhoneValid = true;
}

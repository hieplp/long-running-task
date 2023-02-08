/*
 * This file is generated by jOOQ.
 */
package com.hieplp.lrt.repositories.generate.tables.records;


import com.hieplp.lrt.repositories.generate.tables.User;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;

import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record8<String, String, String, String, String, LocalDateTime, String, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(String userid, String name, String personalid, String phone, String createdby, LocalDateTime createdat, String modifiedby, LocalDateTime modifiedat) {
        super(User.USER);

        setUserid(userid);
        setName(name);
        setPersonalid(personalid);
        setPhone(phone);
        setCreatedby(createdby);
        setCreatedat(createdat);
        setModifiedby(modifiedby);
        setModifiedat(modifiedat);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(com.hieplp.lrt.repositories.generate.tables.pojos.User value) {
        super(User.USER);

        if (value != null) {
            setUserid(value.getUserid());
            setName(value.getName());
            setPersonalid(value.getPersonalid());
            setPhone(value.getPhone());
            setCreatedby(value.getCreatedby());
            setCreatedat(value.getCreatedat());
            setModifiedby(value.getModifiedby());
            setModifiedat(value.getModifiedat());
        }
    }

    /**
     * Getter for <code>longTask.user.userId</code>.
     */
    public String getUserid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>longTask.user.userId</code>.
     */
    public UserRecord setUserid(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>longTask.user.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>longTask.user.name</code>.
     */
    public UserRecord setName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>longTask.user.personalId</code>.
     */
    public String getPersonalid() {
        return (String) get(2);
    }

    /**
     * Setter for <code>longTask.user.personalId</code>.
     */
    public UserRecord setPersonalid(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>longTask.user.phone</code>.
     */
    public String getPhone() {
        return (String) get(3);
    }

    /**
     * Setter for <code>longTask.user.phone</code>.
     */
    public UserRecord setPhone(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>longTask.user.createdBy</code>.
     */
    public String getCreatedby() {
        return (String) get(4);
    }

    /**
     * Setter for <code>longTask.user.createdBy</code>.
     */
    public UserRecord setCreatedby(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>longTask.user.createdAt</code>.
     */
    public LocalDateTime getCreatedat() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>longTask.user.createdAt</code>.
     */
    public UserRecord setCreatedat(LocalDateTime value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>longTask.user.modifiedBy</code>.
     */
    public String getModifiedby() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>longTask.user.modifiedBy</code>.
     */
    public UserRecord setModifiedby(String value) {
        set(6, value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>longTask.user.modifiedAt</code>.
     */
    public LocalDateTime getModifiedat() {
        return (LocalDateTime) get(7);
    }

    /**
     * Setter for <code>longTask.user.modifiedAt</code>.
     */
    public UserRecord setModifiedat(LocalDateTime value) {
        set(7, value);
        return this;
    }

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    @Override
    public Row8<String, String, String, String, String, LocalDateTime, String, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<String, String, String, String, String, LocalDateTime, String, LocalDateTime> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return User.USER.USERID;
    }

    @Override
    public Field<String> field2() {
        return User.USER.NAME;
    }

    @Override
    public Field<String> field3() {
        return User.USER.PERSONALID;
    }

    @Override
    public Field<String> field4() {
        return User.USER.PHONE;
    }

    @Override
    public Field<String> field5() {
        return User.USER.CREATEDBY;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return User.USER.CREATEDAT;
    }

    @Override
    public Field<String> field7() {
        return User.USER.MODIFIEDBY;
    }

    @Override
    public Field<LocalDateTime> field8() {
        return User.USER.MODIFIEDAT;
    }

    @Override
    public String component1() {
        return getUserid();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getPersonalid();
    }

    @Override
    public String component4() {
        return getPhone();
    }

    @Override
    public String component5() {
        return getCreatedby();
    }

    @Override
    public LocalDateTime component6() {
        return getCreatedat();
    }

    @Override
    public String component7() {
        return getModifiedby();
    }

    @Override
    public LocalDateTime component8() {
        return getModifiedat();
    }

    @Override
    public String value1() {
        return getUserid();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getPersonalid();
    }

    @Override
    public String value4() {
        return getPhone();
    }

    @Override
    public String value5() {
        return getCreatedby();
    }

    @Override
    public LocalDateTime value6() {
        return getCreatedat();
    }

    @Override
    public String value7() {
        return getModifiedby();
    }

    @Override
    public LocalDateTime value8() {
        return getModifiedat();
    }

    @Override
    public UserRecord value1(String value) {
        setUserid(value);
        return this;
    }

    @Override
    public UserRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public UserRecord value3(String value) {
        setPersonalid(value);
        return this;
    }

    @Override
    public UserRecord value4(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public UserRecord value5(String value) {
        setCreatedby(value);
        return this;
    }

    @Override
    public UserRecord value6(LocalDateTime value) {
        setCreatedat(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    @Override
    public UserRecord value7(String value) {
        setModifiedby(value);
        return this;
    }

    @Override
    public UserRecord value8(LocalDateTime value) {
        setModifiedat(value);
        return this;
    }

    @Override
    public UserRecord values(String value1, String value2, String value3, String value4, String value5, LocalDateTime value6, String value7, LocalDateTime value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }
}
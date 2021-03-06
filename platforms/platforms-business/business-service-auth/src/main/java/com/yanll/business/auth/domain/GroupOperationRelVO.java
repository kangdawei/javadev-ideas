package com.yanll.business.auth.domain;

import com.yanll.framework.data.mysql.domain.VOEntity;

/*
* 当前文件为MybatisGenerator自动生成的VO，请手动剪切到*-service项目。
*/
public class GroupOperationRelVO extends VOEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_group_operation_rel.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_group_operation_rel.group_id
     *
     * @mbg.generated
     */
    private Long groupId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_group_operation_rel.operation_id
     *
     * @mbg.generated
     */
    private Long operationId;

    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_group_operation_rel.id
     *
     * @return the value of m_group_operation_rel.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_group_operation_rel.id
     *
     * @param id the value for m_group_operation_rel.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_group_operation_rel.group_id
     *
     * @return the value of m_group_operation_rel.group_id
     *
     * @mbg.generated
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_group_operation_rel.group_id
     *
     * @param groupId the value for m_group_operation_rel.group_id
     *
     * @mbg.generated
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_group_operation_rel.operation_id
     *
     * @return the value of m_group_operation_rel.operation_id
     *
     * @mbg.generated
     */
    public Long getOperationId() {
        return operationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_group_operation_rel.operation_id
     *
     * @param operationId the value for m_group_operation_rel.operation_id
     *
     * @mbg.generated
     */
    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }
}
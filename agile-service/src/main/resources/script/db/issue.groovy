package script.db


databaseChangeLog(logicalFilePath: 'issue.groovy') {
    changeSet(id: '2019-03-10-create-issue', author: 'zongw.lee@gmail.com') {
        createTable(tableName: "issue") {
            column(name: 'id', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: 'uuid') {
                constraints(primaryKey: true)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: '问题名称')
            column(name: 'type', type: 'int(10)', remarks: '问题类型， 问题和测试用例')
            column(name: 'reporter_id', type: 'BIGINT', remarks: '报告人ID')
            column(name: 'handler_id', type: 'BIGINT', remarks: '处理人ID')
            column(name: 'priority_id', type: 'BIGINT', remarks: '优先级ID')
            column(name: 'status_id', type: 'BIGINT', remarks: '状态ID')
            column(name: 'handle_date', type: 'DATETIME', remarks: '处理时间')
            column(name: 'solve_date', type: 'DATETIME', remarks: '解决时间')

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
    }

}
package script.db


databaseChangeLog(logicalFilePath: 'sprint.groovy') {
    changeSet(id: '2019-03-10-create-sprint', author: 'zongw.lee@gmail.com') {
        createTable(tableName: "sprint") {
            column(name: 'id', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: 'ID,主键') {
                constraints(primaryKey: true)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: '名称') {
                constraints(nullable: 'false')
            }
            column(name: 'handle_date', type: 'DATETIME', remarks: '开始时间')
            column(name: 'solve_date', type: 'DATETIME', remarks: '结束时间')
            column(name: 'active', type: 'tinyint', remarks: '是否活跃 活跃：1，不活跃：0')

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
    }
}
package script.db


databaseChangeLog(logicalFilePath: 'gitlab_ci.groovy') {
    changeSet(id: '2019-03-10-create-gitlab_ci', author: 'zongw.lee@gmail.com') {
        createTable(tableName: "gitlab_ci") {
            column(name: 'id', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: 'ID,主键') {
                constraints(primaryKey: true)
            }
            column(name: 'creator_id', type: 'BIGINT UNSIGNED', remarks: '创建人Id') {
                constraints(nullable: 'false')
            }
            column(name: 'pipeline_id', type: 'BIGINT UNSIGNED', remarks: 'gitlab的pipelineId') {
                constraints(nullable: 'false')
            }
            column(name: 'commit_id', type: 'BIGINT UNSIGNED', remarks: '提交Id') {
                constraints(nullable: 'false')
            }
            column(name: 'status', type: 'VARCHAR(255)', remarks: '状态：pending，success，running，skipped，canceled，failed，passed')
            column(name: 'pipeline_creation_date', type: 'DATETIME', remarks: '创建日期')

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
    }
}
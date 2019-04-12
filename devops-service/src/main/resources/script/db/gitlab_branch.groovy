package script.db


databaseChangeLog(logicalFilePath: 'gitlab_branch.groovy') {
    changeSet(id: '2019-03-10-create-gitlab_branch', author: 'zongw.lee@gmail.com') {
        createTable(tableName: "gitlab_branch") {
            column(name: 'id', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: 'ID,主键') {
                constraints(primaryKey: true)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: '名称') {
                constraints(nullable: 'false')
            }
            column(name: 'creator_id', type: 'BIGINT UNSIGNED', remarks: '创建人Id') {
                constraints(nullable: 'false')
            }
            column(name: 'issue_id', type: 'BIGINT UNSIGNED', remarks: '关联问题Id') {
                constraints(nullable: 'false')
            }
            column(name: 'active', type: 'tinyint', remarks: '是否活跃 活跃：1，不活跃：0')

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
        createIndex(tableName: "gitlab_branch", indexName: "idx_gitlab_branch_creator_id") {
            column(name: "creator_id")
        }
        createIndex(tableName: "gitlab_branch", indexName: "idx_gitlab_branch_issue_id") {
            column(name: "issue_id")
        }
    }
}
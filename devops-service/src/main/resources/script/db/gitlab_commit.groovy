package script.db


databaseChangeLog(logicalFilePath: 'gitlab_commit.groovy') {
    changeSet(id: '2019-03-10-create-gitlab_commit', author: 'zongw.lee@gmail.com') {
        createTable(tableName: "gitlab_commit") {
            column(name: 'id', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: 'ID,主键') {
                constraints(primaryKey: true)
            }
            column(name: 'commit_sha', type: 'BIGINT UNSIGNED', remarks: '提交秘钥') {
                constraints(nullable: 'false')
            }
            column(name: 'creator_id', type: 'BIGINT UNSIGNED', remarks: '提交者Id') {
                constraints(nullable: 'false')
            }
            column(name: 'ref', type: 'VARCHAR(255)', remarks: '关联的分支') {
                constraints(nullable: 'false')
            }
            column(name: 'commit_content', type: 'VARCHAR(2000)', remarks: '提交内容')
            column(name: 'commit_date', type: 'DATETIME', remarks: '创建日期')
            column(name: 'url', type: 'VARCHAR(2000)', remarks: '仓库地址')

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
        createIndex(tableName: "gitlab_commit", indexName: "idx_gitlab_commit_creator_id") {
            column(name: "creator_id")
        }

    }
}
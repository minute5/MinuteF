package script.db


databaseChangeLog(logicalFilePath: 'gitlab_repository.groovy') {
    changeSet(id: '2019-03-10-create-gitlab_repository', author: 'zongw.lee@gmail.com') {
        createTable(tableName: "gitlab_repository") {
            column(name: 'id', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: 'ID,主键') {
                constraints(primaryKey: true)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: '名称') {
                constraints(nullable: 'false')
            }
            column(name: 'gitlab_name', type: 'VARCHAR(255)', remarks: 'gitlab名称') {
                constraints(unique: true, uniqueConstraintName: 'UK_GITLAB_REPOSITORY_GITLAB_NAME')
                constraints(nullable: 'false')
            }
            column(name: 'gitlab_id', type: 'BIGINT UNSIGNED', remarks: 'gitlab用户id') {
                constraints(unique: true, uniqueConstraintName: 'UK_GITLAB_REPOSITORY_GITLAB_ID')
                constraints(nullable: 'false')
            }
            column(name: 'gitlab_group_id', type: 'BIGINT UNSIGNED', remarks: 'gitlab组id') {
                constraints(unique: true, uniqueConstraintName: 'UK_GITLAB_REPOSITORY_GITLAB_GROUP_ID')
                constraints(nullable: 'false')
            }
            column(name: 'gitlab_project_id', type: 'BIGINT UNSIGNED', remarks: 'gitlab项目id') {
                constraints(unique: true, uniqueConstraintName: 'UK_GITLAB_REPOSITORY_GITLAB_PROJECT_ID')
                constraints(nullable: 'false')
            }
            column(name: 'url', type: 'VARCHAR(2000)', remarks: '仓库地址')

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
    }
}
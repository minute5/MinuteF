package script.db


databaseChangeLog(logicalFilePath: 'role.groovy'){
    changeSet(id: '2019-03-10-create-role', author: 'zongw.lee@gmail.com') {
        createTable(tableName: "role") {
            column(name: 'id', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: 'ID,主键，工号') {
                constraints(primaryKey: true)
            }
            column(name: 'name', type: 'VARCHAR(255)', remarks: '名称') {
                constraints(nullable: 'false')
                constraints(unique: true)
            }
            column(name: 'password', type: 'BIGINT UNSIGNED', remarks: '密码') {
                constraints(nullable: 'false')
            }
            column(name: 'type', type: 'tinyint', remarks: '权限类型：0：普通开发用户，1：主管--管理员，2：Minute5总管,3：经理--超级管理员，4：参观人员') {
                constraints(nullable: 'false')
            }
            column(name: 'email', type: 'VARCHAR(255)', remarks: '邮箱地址'){
                constraints(nullable: 'false')
                constraints(unique: true)
            }
            column(name: 'mobile', type: 'int', remarks: '手机号'){
                constraints(unique: true)
            }

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
    }
}
package dao

import entity.Role

class RoleDAOImpl implements RoleDAO {
    Role addRole(String name, String description) {
        def statement = 'insert into Role (name, description) values (?, ?)'
        def result = DAOFactory.getConnection().executeInsert statement, [name, description]
        if(result != null) {
            return new Role(id: result[0][0], name: name, description: description)
        }
        null
    }

    boolean removeRoleById(int id) {
        def stmt = "delete from Role where id = ?"
        def result = DAOFactory.getConnection().executeInsert stmt, [id]
        return result.isEmpty()
    }

    boolean removeRoleByName(String username) {
        def stmt = "delete from Role where name = ?"
        def result = DAOFactory.getConnection().executeInsert stmt, [username]
        return result.isEmpty()
    }

    Role getRoleById(int id) {
        def statement = "select * from Role where id=${id}"
        Role role = null
        DAOFactory.getConnection().eachRow(statement) { row ->
            role = new Role(name: row.name, description: row.description, id: row.id)

        }
        return role
    }

    Role getRoleByName(String name) {
        def statement = "select * from Role where name=${name}"
        Role role = null
        DAOFactory.getConnection().eachRow(statement) { row ->
            role = new Role(name: row.name, description: row.description, id: row.id)

        }
        return role
    }
}

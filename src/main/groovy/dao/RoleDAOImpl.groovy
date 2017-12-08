package dao

import entity.Role

class RoleDAOImpl implements RoleDAO {

    String stmt
    List result

    Role addRole(String name, String description) {
        stmt = 'insert into Role (name, description) values (?, ?)'
        result = DAOFactory.getConnection().executeInsert stmt, [name, description]
        if(result != null) {
            return new Role(id: result[0][0], name: name, description: description)
        }
        null
    }

    boolean removeRoleById(int id) {
        stmt = "delete from Role where id=?"
        result = DAOFactory.getConnection().executeInsert stmt, [id]
        result.isEmpty()
    }

    boolean removeRoleByName(String username) {
        stmt = "delete from Role where name=?"
        result = DAOFactory.getConnection().executeInsert stmt, [username]
        result.isEmpty()
    }

    Role getRoleById(int id) {
        stmt = "select * from Role where id=?"
        Role role = null
        DAOFactory.getConnection().eachRow(stmt, [id]) {
            role = new Role(name: it.name, description: it.description, id: it.id)

        }
        role
    }

    Role getRoleByName(String name) {
        stmt = "select * from Role where name=?"
        Role role = null
        DAOFactory.getConnection().eachRow(stmt, [name]) {
            role = new Role(name: it.name, description: it.description, id: it.id)

        }
        role
    }

    Role updateRoleNameAndDescription(int id, String name, String description) {
        stmt = "update Role set name=?, description=? where id=?"
        result = DAOFactory.getConnection().executeInsert stmt, [name, description, id]
        new Role(id, name, description)
    }
}

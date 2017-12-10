package dao

import org.junit.After
import org.junit.Before

import static org.junit.Assert.*
import org.junit.Test
import entity.Role

class RoleDAOImplTest {
    final private String NAME = "Role Name"
    final private String UPDATED_NAME = "Updated Role Name"
    final private String DESCRIPTION = "Role Description"
    final private String UPDATED_DESCRIPTION = "Updated Role Description"
    RoleDAO roleDao
    Role role

    @Before
    void setup(){
        roleDao = new DAOFactory().getRoleDAO()
        new DAOFactory().createTestData()
    }

    @After
    void teardown(){
        new DAOFactory().deleteTestData()
        DAOFactory.closeConnection()
    }

    @Test
    void canRolesBeAdded(){
        role = roleDao.addRole(NAME, DESCRIPTION)
        assertEquals new Role().getClass(), role.getClass()
        roleDao.removeRoleById(role.id)
    }

    @Test
    void canRolesBeRetrievedById(){
        role = roleDao.addRole(NAME, DESCRIPTION)
        Role retrievedRow = roleDao.getRoleById(role.id)
        assertEquals role, retrievedRow
        roleDao.removeRoleById(role.id)
    }

    @Test
    void doesRolesRetrieveByIdReturnNullWithBadId(){
        assertNull roleDao.getRoleById(10000)
    }

    @Test
    void doesRolesBeRetrievedByNameRetrieveByName(){
        role = roleDao.addRole(NAME, DESCRIPTION)
        Role retrievedRow = roleDao.getRoleByName(NAME)
        assertEquals role.id, retrievedRow.id
        roleDao.removeRoleById(role.id)
    }

    @Test
    void doesRolesBeRetrievedByNameReturnNullWithBadName() {
        assertNull roleDao.getRoleByName(NAME)
    }

    @Test
    void canRolesBeRemovedById(){
        role = roleDao.addRole(NAME, DESCRIPTION)
        roleDao.removeRoleById(role.id)
        assertNull roleDao.getRoleById(role.id)
        assertTrue roleDao.removeRoleById(roleDao.addRole(NAME, DESCRIPTION).id)
    }

    @Test
    void canRolesBeRemovedByName(){

        role = roleDao.addRole(NAME, DESCRIPTION)
        roleDao.removeRoleByName(role.name)
        assertNull roleDao.getRoleById(role.id)

        role = roleDao.addRole(NAME, DESCRIPTION)
        assertTrue roleDao.removeRoleByName(role.name)
    }

    @Test
    void canRoleNameAndDescriptionBeChanged(){
        role = roleDao.addRole(NAME, DESCRIPTION)

        assertEquals new Role().getClass(), roleDao.updateRoleNameAndDescription(role.id, UPDATED_NAME, UPDATED_DESCRIPTION).getClass()
        assertEquals UPDATED_NAME, roleDao.updateRoleNameAndDescription(role.id, UPDATED_NAME, UPDATED_DESCRIPTION).name
        assertEquals UPDATED_NAME, roleDao.getRoleByName(UPDATED_NAME).name

        roleDao.removeRoleByName(UPDATED_NAME)
        roleDao.removeRoleByName(NAME)
    }

    @Test
    void doesGetAllRolesReturnRolesAsList(){
        assertEquals 2, roleDao.getAllRoles().size()
        assertEquals roleDao.getRoleByName("TestRoleNumber100"), roleDao.getAllRoles()[0]
        assertEquals roleDao.getRoleByName("TestRoleNumber101"), roleDao.getAllRoles()[1]
    }

}

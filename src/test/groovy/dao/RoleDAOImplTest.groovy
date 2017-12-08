package dao

import org.junit.After
import org.junit.Before

import static org.junit.Assert.*
import org.junit.Test
import entity.Role

class RoleDAOImplTest {
    RoleDAO roleDao
    Role role

    @Before
    void setup(){
        roleDao = new DAOFactory().getRoleDAO()
    }

    @After
    void teardown(){
        DAOFactory.closeConnection()
    }

    @Test
    void canRolesBeAdded(){
        role = roleDao.addRole("testing_name", "testing_description")
        assertEquals new Role().getClass(), role.getClass()
        roleDao.removeRoleById(role.id)
    }

    @Test
    void canRolesBeRetrievedById(){
        role = roleDao.addRole("testing_name", "testing_description")
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
        role = roleDao.addRole("testing_name", "testing_description")
        Role retrievedRow = roleDao.getRoleByName("testing_name")
        assertEquals role.id, retrievedRow.id
        roleDao.removeRoleById(role.id)
    }

    @Test
    void doesRolesBeRetrievedByNameReturnNullWithBadName() {
        assertNull roleDao.getRoleByName("testing_name_doesnt_exist")
    }

    @Test
    void canRolesBeRemovedById(){

        role = roleDao.addRole("testing_name", "testing_description")
        roleDao.removeRoleById(role.id)
        assertNull roleDao.getRoleById(role.id)

        role = roleDao.addRole("testing_name", "testing_description")
        assertTrue roleDao.removeRoleById(role.id)
    }

    @Test
    void canRolesBeRemovedByName(){

        role = roleDao.addRole("testing_name", "testing_description")
        roleDao.removeRoleByName(role.name)
        assertNull roleDao.getRoleById(role.id)

        role = roleDao.addRole("testing_name", "testing_description")
        assertTrue roleDao.removeRoleByName(role.name)
    }
}

package dao

import static org.junit.Assert.*
import org.junit.Test
import entity.Role

class RoleDAOImplTest {

    @Test
    void canRolesBeAdded(){
        Role role = new DAOFactory().getRoleDAO().addRole("testing_name", "testing_description")
        assertEquals new Role().getClass(), role.getClass()
    }

    @Test
    void canRolesBeRetrievedById(){
        Role role = new DAOFactory().getRoleDAO().addRole("testing_name", "testing_description")
        Role retrievedRow = new DAOFactory().getRoleDAO().getRoleById(role.id)
        assertEquals role, retrievedRow
    }

    @Test
    void doesRolesRetrieveByIdReturnNullWithBadId(){
        assertNull new DAOFactory().getRoleDAO().getRoleById(10000)
    }

    @Test
    void doesRolesBeRetrievedByNameRetrieveByName(){
        Role role = new DAOFactory().getRoleDAO().addRole("testing_name", "testing_description")
        Role retrievedRow = new DAOFactory().getRoleDAO().getRoleByName("testing_name")
        assertEquals role.id, retrievedRow.id
    }

    @Test
    void doesRolesBeRetrievedByNameReturnNullWithBadName() {
        assertNull new DAOFactory().getRoleDAO().getRoleByName("testing_name_doesnt_exist")
    }
}

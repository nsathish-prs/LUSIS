using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE;
using LUSIS_EF_FACADE.Facade;

namespace LUSIS_CONTROLLER.Controller
{
    public class LoginController
    {
        EF_Facade facade;

        public LoginController()
        {
            facade = new EF_Facade();
        }

        public Employee getEmployeeRecord(string uName)
        {
           return facade.getEmployee(uName);
        }
        public bool updatePassword(string uName, string password)
        {
            if (facade.updatePassword(uName, password))
                return true;
            else
                return false;
        }

        public Employee checkLogin(String uname, String passwd)
        {
            Employee e = facade.getEmployee(uname);

            if (e == null)
            {
                //return true;
                return null;
            }
            else if (e.EmpID.Equals(uname) && e.Password.Equals(passwd))
            {
                //return false;
                return e;
            }
            else
            {
                return null;
            }
        }
    }
}

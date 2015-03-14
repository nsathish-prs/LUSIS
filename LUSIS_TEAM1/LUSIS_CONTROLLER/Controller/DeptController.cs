using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE.Facade;
using LUSIS_EF_FACADE;

namespace LUSIS_CONTROLLER.Controller
{
    public class DeptController
    {
        EF_Facade facade;

        public DeptController()
        {
            facade = new EF_Facade();
        }

        // DeptController (Return List<CollectionPoint> Or Null) // Benny
        public List<CollectionPoint> getAllCPointsLocation()
        {
            return facade.getAllCollectionPoints();
        }

        // DeptController (Return CollectionPoint Or Null) // Benny
        public CollectionPoint getDeptCPointLocation(String deptCode)
        {
            CollectionPoint c = facade.getDeptCollectionPoint(deptCode);

            if (c != null)
                return c;
            else
                return null;
        }

        // DeptController (Return List<Employee> For "Delegate" or "Representative" Or Null) // Benny
        public List<Employee> getDeptEmployees(String deptCode, String addRole)
        {
            List<Employee> eList = facade.getDeptEmployees(deptCode, addRole);
            return eList;
            //List<String> deptEmpNames = new List<String>();

            //if (eList != null)
            //{
            //    foreach (Employee e in eList)
            //        deptEmpNames.Add(e.Name);

            //    return deptEmpNames;
            //}
            //else
            //    return null;
        }

        // DeptController (Return Employee Or Null) // Benny
        public Employee getDeptRepName(String deptCode)
        {
            return facade.getDeptRepEmp(deptCode);
        }

        // DeptController Take 4 String, Batch Processing, (Return bool = True Or False) // Benny
        public bool UpdateDeptDetails(String prevRep, String newRep, String newCPoint, String deptCode)
        {
            return facade.UpdateDeptDetails(prevRep, newRep, newCPoint, deptCode);
        }

        // DeptController // (Return Employee = DelegateEmp) Or Null // Benny
        public Employee getDelegateEmp(String deptCode)
        {
            Employee emp = facade.getDelegateEmp(deptCode);

            return emp;
        }

        // DeptController // Get Delegate By String (deptCode) (Return Delegate Or Null) // Benny
        public LUSIS_EF_FACADE.Delegate getDelegate(String deptCode)
        {
            LUSIS_EF_FACADE.Delegate delegat = facade.getDelegate(deptCode);
            return delegat;
            //            List<String> dInfo = new List<String>();
            //
            //            if (d != null)
            //            {
            //                dInfo.Add(d.Employee.Name);
            //                dInfo.Add(d.StartDate.Value.ToString("dd-MMM-yyyy"));
            //                dInfo.Add(d.EndDate.Value.ToString("dd-MMM-yyyy"));
            //                return dInfo;
            //            }
            //            else
            //                return null;
        }

        // DeptController // Clear Delegate By String (deptCode) // Benny
        public Boolean clearDelegate(String deptCode)
        {
            return facade.clearDelegate(deptCode);
        }

        // DeptController // Update Delegate By String (delName, deptCode, startDate, endDate) // Benny
        public Boolean updateDelegate(String delName, String deptCode, String startDate, String endDate)
        {
            LUSIS_EF_FACADE.Delegate del = new LUSIS_EF_FACADE.Delegate();

            del.DelegateID = facade.getEmployeeByNameAndDept(delName, deptCode).EmpID;
            if (startDate != null)
                del.StartDate = Convert.ToDateTime(startDate);
            else
                del.StartDate = DateTime.Now;

            if (endDate != null)
                del.EndDate = Convert.ToDateTime(endDate);
            else
                del.EndDate = null;
            clearDelegate(deptCode);
            return facade.updateDelegate(del);
        }

        // DeptController (Return Employee Or Null) // Sathish
        public Employee getEmployeeByNameAndDept(string empName, string deptCode)
        {
            return facade.getEmployeeByNameAndDept(empName, deptCode);
        }

        public Employee getEmployee(string empId)
        {
            return facade.getEmployee(empId);
        }
       public Employee getDeptHead(string DeptCode)
        {
            return facade.getDeptHead(DeptCode);
}
    }
}

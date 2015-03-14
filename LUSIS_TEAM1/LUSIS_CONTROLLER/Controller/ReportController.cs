using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LUSIS_EF_FACADE.Facade;
using LUSIS_EF_FACADE;

namespace LUSIS_CONTROLLER.Controller
{
    public class ReportController
    {
        EF_Facade ef;
        public ReportController()
        {
            ef = new EF_Facade();
        }
        public List<Item> getdistItemList()
        {
            return ef.getdistItemList();
        }
        public List<string> getdeptList()
        {
            return ef.getdeptList();
        }
        public List<string> getItemsbyDesc(string iCate)
        {
            return ef.getItemsbyDesc(iCate);
        }

        public string getItemByDescr(String description)
        {
            return ef.getItemByDescr(description);
        }
        public string getDeptCodewithName(string deptName)
        {
            return ef.getDeptCodewithName(deptName);
        }
    }
}

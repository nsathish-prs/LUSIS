using System;
using System.Collections.Generic;
using LUSIS_EF_FACADE;
using LUSIS_CONTROLLER.Controller;

namespace LUSIS_WEB_UI.Views
{
    public partial class DeptDetails : System.Web.UI.Page
    {
        readonly MainController MainCont = new MainController();
        String deptCode = null;
        
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["Logon"] == null)
                Response.Redirect("~/Login.aspx");
            else
            {
                checkAuthorization();
                deptCode = Session["DeptCode"].ToString();
            }

            getCollectionPoints();
            getDeptEmployees(deptCode);
            getDeptCPointLocation(deptCode);
            getDeptRepName(deptCode);
        }

        public void getCollectionPoints()
        {
            var colPoints = MainCont.getAllCPointsLocation();

            foreach (var c in colPoints)
            {
                DDLCollPoints.Items.Add(c.Location);
            }
        }

        public void getDeptCPointLocation(String deptCode)
        {
            var cPoint = MainCont.getDeptCPointLocation(deptCode);
            if (cPoint != null)
                LblCurrCollPnt.Text = cPoint.Location;
            else
                LblCurrCollPnt.Text = "None";
        }

        public void getDeptEmployees(String deptCode)
        {
            var empList = MainCont.getDeptEmployees(deptCode, "Representative");
            foreach (var e in empList)
            {
                DDLEmpRepList.Items.Add(e.Name);
            }
        }

        public void getDeptRepName(String deptCode)
        {
            var crntRep = MainCont.getDeptRepName(deptCode);

            if (crntRep != null)
                LblCurrRepName.Text = crntRep.Name;
            else
                LblCurrRepName.Text = "None";
        }

        protected void btnUpdate_Click(object sender, EventArgs e)
        {
            var newCPoint = DDLCollPoints.SelectedItem.ToString();
            var newRep = DDLEmpRepList.SelectedItem.Text;
            var prevRep = LblCurrRepName.Text;

            if (MainCont.updateDeptDetails(prevRep, newRep, newCPoint, deptCode))
            {
                //Response.Write("<script language='javascript'>alert('Department Details updated successfully.');</script>");
                Response.Redirect("~/Views/DeptDetails.aspx");
            }
        }

        private void checkAuthorization()
        {

            if (Session["Role"] == null)
                Response.Redirect("~/Login.aspx");

            var authorization = false;

            if (Session["Role"] != null)
            {
                switch (Convert.ToString(Session["Role"]))
                {
                    case "DeptHead":
                        authorization = true;
                        break;
                    case "Delegate":
                        authorization = true;
                        break;
                    case "Representative":
                        authorization = false;
                        break;
                    case "Employee":
                        authorization = false;
                        break;
                    case "Manager":
                        authorization = false;
                        break;
                    case "Supervisor":
                        authorization = false;
                        break;
                    case "Clerk":
                        authorization = false;
                        break;
                }
            }

            if (!authorization)
            {
                Response.Redirect("~/Views/Errorpage.aspx");
            }
        }

    }
}
using System;
using System.Collections.Generic;
using LUSIS_WEB_UI.Reports_Dataset;

namespace LUSIS_WEB_UI.Reports
{
    public partial class ReportViewer : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            checkAuthorization();
            populateReport();
        }

        public void populateReport()
        {
            var ds = new LUSIS_Dataset();
            var iCategory=Session["Items"].ToString();
            var dept = Session["allDept"].ToString();
            var repbasedon = Session["repbased"].ToString();
            var month = Session["onemonth"].ToString();
            var repType=Session["RepType"].ToString();
            string[] itemList;
            string[] deptList;
            string[] monthList;
            if (iCategory != "All")
            {
                var repItems = (List<string>)Session["RepItems"];
                itemList = repItems.ToArray();
            }

            if (month != "All")
            {
                var repmonths = (List<string>)Session["Month"];
                monthList = repmonths.ToArray();
            }


            if (dept != "All")
            {
                var repDepts = (List<string>)Session["Department"];
                Console.WriteLine(repDepts);
                deptList = repDepts.ToArray();
            }

            if (repbasedon == "Quantity" && repType == "Purchase Order")
            {
                var id = new Reports_Dataset.LUSIS_DatasetTableAdapters.ItemsMonthQuantityTableAdapter();
               // id.Fill(ds.ItemsMonthQuantity, getmonthno(monthList[0]), getmonthno(monthList[1]), getmonthno(monthList[2]), itemList[0], itemList[1], itemList[2]);

            }

        }

        public int getmonthno(string month)
        {
            switch (month)
            {
                case "January": return 1; break;
                case "February": return 2; break;
                case "March": return 3; break;
                case "April": return 4; break;
                case "May": return 5; break;
                case "June": return 6; break;
                case "July": return 7; break;
                case "August": return 8; break;
                case "September": return 9; break;
                case "October": return 10; break;
                case "November": return 11; break;
                default : return 12; break;
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
                        authorization = false;
                        break;
                    case "Delegate":
                        authorization = false;
                        break;
                    case "Representative":
                        authorization = false;
                        break;
                    case "Employee":
                        authorization = false;
                        break;
                    case "Manager":
                        authorization = true;
                        break;
                    case "Supervisor":
                        authorization = true;
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
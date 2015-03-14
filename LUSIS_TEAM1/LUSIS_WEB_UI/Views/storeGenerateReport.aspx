<%@ Page Title="" Language="C#" MasterPageFile="~/Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeGenerateReport.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeGenerateReport" %>

<%@ Register Assembly="CrystalDecisions.Web, Version=13.0.2000.0, Culture=neutral, PublicKeyToken=692fbea5521e1304"
    Namespace="CrystalDecisions.Web" TagPrefix="CR" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<asp:MultiView ID="MultiView1" runat="server">

                <asp:View ID="View1" runat="server">
<div class="row">

    <div class="col-lg-12">
        
        <h1 class="page-header"> Report Generation </h1>
    
        <div class="col-lg-8" style="color:White">

            <div class="col-lg-3" style="margin-top:1.5%">Choose Report Item Category: </div>
            <div class="col-lg-3" style="margin-top:1.5%">
                
            <asp:DropDownList ID="iCategoryList" 
                    runat="server" CssClass="form-control" AutoPostBack="True" 
                    onselectedindexchanged="iCategoryList_SelectedIndexChanged">
                     </asp:DropDownList></div>
                     <asp:Label ID="CategoryLabel" runat="server" 
                Text="Please select any three categories" Visible="False"></asp:Label>
                 <div class="col-lg-3" style="margin-top:1.5%">
                <asp:ListBox ID="iList" CssClass="form-control" runat="server" SelectionMode="Multiple" Visible="False"></asp:ListBox></div>

            

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Report Based On:</div>
            <div class="col-lg-3" style="margin-top:1.5%"><asp:DropDownList ID="repBasedOn" runat="server" CssClass="form-control"> </asp:DropDownList></div>
            
            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Report Period:</div>
            <div class="col-lg-3" style="margin-top:1.5%">
            <asp:Label ID="PeriodLabel" runat="server" Text="Please select any three months"></asp:Label>
            <asp:ListBox ID="monthName" CssClass="form-control" runat="server" SelectionMode="Multiple">  </asp:ListBox>
             
          
            </div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Select Type</div>
            <div class="col-lg-3" style="margin-top:1.5%"><asp:DropDownList ID="repType" 
                    runat="server" OnSelectedIndexChanged="ReportType_Change" 
                    CssClass="form-control" AutoPostBack="True"> </asp:DropDownList></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%"><asp:Label ID="Label1" runat="server" 
                    Text="Select Department" Visible="False"></asp:Label></div>
            <div class="col-lg-3" style="margin-top:1.5%">
                <asp:ListBox ID="deptList" CssClass="form-control" Visible="False" runat="server" SelectionMode="Multiple"></asp:ListBox>
           
                    </div>

            <div style="clear:both"></div>

        </div>

        <div style="clear:both"></div>

        <div class="col-lg-1" style="padding-top:1.5%">
            
              <asp:Button ID="btnGenerate" runat="server" Text="Generate Report" 
                  class="btn btn-primary" type="submit" onclick="btnGenerate_Click" />
            
        </div>

    </div>

</div>
 </asp:View>
 <asp:View ID="View2" runat="server">
 <div class="row">

    <div class="col-lg-12">
        
        <CR:CrystalReportViewer ID="CrystalReportViewer1" runat="server" AutoDataBind="true" />
     </div>

 </div>
 
 </asp:View>

 </asp:MultiView>


</asp:Content>

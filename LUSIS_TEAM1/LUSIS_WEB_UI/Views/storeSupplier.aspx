<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeSupplier.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeSupplier" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-12">
        
        <h1 class="page-header"> &nbsp;<asp:Label ID="LblHeadTitle" runat="server" 
                Text="Edit Supplier Details"></asp:Label>
        </h1>
    
        <div class="col-lg-7">
        <div class="col-lg-8" style="margin-top:1.5%">
                <asp:Label ID="LblErrorText" runat="server" Text=""></asp:Label></div>
            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Supplier Code</div>
            <div class="col-lg-6" style="margin-top:1.5%">
                <asp:TextBox CssClass="form-control" 
                    ID="TxtSupCode" runat="server" ></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Supplier Name</div>
            <div class="col-lg-6" style="margin-top:1.5%"><asp:TextBox CssClass="form-control" 
                    ID="TxtSupName" runat="server" ></asp:TextBox></div>
           <%-- 
            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Email</div>
            <div class="col-lg-4" style="margin-top:1.5%"><asp:TextBox ID="supEmail" CssClass="form-control" runat="server" ></asp:TextBox></div>
--%>
            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Address</div>
            <div class="col-lg-6" style="margin-top:1.5%">
                <asp:TextBox ID="TxtSupAddress" 
                    runat="server" CssClass="form-control" Rows="4"></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Fax Number</div>
            <div class="col-lg-6" style="margin-top:1.5%"><asp:TextBox ID="TxtSupFax" 
                    runat="server" CssClass="form-control" ></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Contact Name</div>
            <div class="col-lg-6" style="margin-top:1.5%"><asp:TextBox ID="TxtConName" 
                    runat="server" CssClass="form-control" ></asp:TextBox></div>

            <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%">Contact Number</div>
            <div class="col-lg-6" style="margin-top:1.5%"><asp:TextBox ID="TxtConNum" 
                    CssClass="form-control" runat="server" ></asp:TextBox></div>

            <div style="clear:both"></div>
        
        </div>

        <div style="clear:both"></div>

        <div class="col-lg-1" style="padding-top:1.5%; width: auto;">
            
              <asp:Button ID="BtnDone" runat="server" Text="Update" class="btn btn-primary" 
                  type="submit" onclick="BtnDone_Click" />
            
        </div>

        <div class="col-lg-1" style="padding-top:1.5%; width: auto;">
            
              <asp:Button ID="BtnCancel" runat="server" Text="Cancel" class="btn btn-primary" 
                  type="submit" onclick="BtnCancel_Click" />
            
        </div>

    </div>

</div>

</asp:Content>

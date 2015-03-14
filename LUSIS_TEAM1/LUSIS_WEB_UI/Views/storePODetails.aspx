<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storePODetails.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storePODetails" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<script src="../Scripts/submitclick.js"></script>

<div class="row">

    <div class="col-lg-8">
    
        <h1 class="page-header"> Purchase Order Details  </h1>

        <div class="col-lg-12" style="margin-bottom:1.5%; color:White; line-height:2em;">
        
            <div class="col-lg-3">Supplier Name :</div>
                        <div class="col-lg-4"><asp:Label ID="supName" runat="server" Text="Label"></asp:Label></div>
                        <div style="clear:both"></div>

            <div class="col-lg-3">Order ID</div>
                        <div class="col-lg-4"><asp:Label ID="lblOrder" runat="server" Text="Label"></asp:Label></div>
                        <div style="clear:both"></div>

            <div class="col-lg-3">Order Date</div>
                        <div class="col-lg-4"><asp:Label ID="lblOrderDate" runat="server" Text="Label"></asp:Label></div>
                        <div style="clear:both"></div>

         
        </div>

        <div style="clear:both"></div>

        <div class="col-lg-12">
        <div class="table-responsive">


  
                    <asp:Table ID="TableRaiseReq" CssClass="table table-bordered table-hover table-striped" runat="server">
                        
                    </asp:Table>
                             
          </div>  

          </div>

          <div style="clear:both"></div>
          <div class="col-lg-2" style="margin-top:1.5%; margin-bottom:1.5%">
           <asp:TextBox ID="txtDeliveryID" runat="server" placeholder="Delievery ID" CssClass="form-control"></asp:TextBox></div>
             <div style="clear:both"></div>
             <div class="col-lg-4">
                <asp:Button ID="submitBtn" runat="server" Text="Submit" class="btn btn-primary" 
                    type="submit" onclick="submitBtn_Click" /></div>
          </div>

    </div>

</div>

</asp:Content>

<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeGenerateOrders.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeGenerateOrders" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


<div class="row">
     <div class="col-lg-10">

            <h1 class="page-header">
                 Purchase Order Details
             </h1>

                <div class="col-lg-8">
                    
                        <div class="col-lg-4">Supplier :</div>
                            <div class="col-lg-4"><asp:DropDownList ID="empList" runat="server" CssClass="form-control"> </asp:DropDownList></div>

                            <div style="clear:both"></div>

                        <div class="col-lg-4">Date of Order</div>
                            <div class="col-lg-4"><asp:Label ID="ordDate" runat="server" Text="Label"></asp:Label></div>

                            <div style="clear:both"></div>

                        <div class="col-lg-4">Expected Delivery :</div>
                            <div class="col-lg-4"><asp:TextBox ID="expDelivery" runat="server"></asp:TextBox></div>

                 </div>

                 <div style="clear:both"></div>

               <div class="panel panel-default" style="margin-top:1.5%">
                        
                    <div class="table-responsive">
  
                        <asp:Table ID="TableRaiseReq" CssClass="table table-bordered table-hover table-striped" runat="server">
                            <asp:TableHeaderRow>
                                <asp:TableHeaderCell>Item ID</asp:TableHeaderCell>
                                <asp:TableHeaderCell>Item Description</asp:TableHeaderCell>
                                <asp:TableHeaderCell>Min. Order</asp:TableHeaderCell>
                                <asp:TableHeaderCell>Required</asp:TableHeaderCell>
                            </asp:TableHeaderRow>
                            <asp:TableRow>
                                        
                            </asp:TableRow>
                        </asp:Table>
                             
                        </div>  
                            
                    </div>

                    <div style="margin-top:1%">
                               <div class="col-lg-4" style="padding-top:1.5%">
                                <asp:Button ID="subOrder" runat="server" Text="Submit" 
                                       class="btn btn-primary" type="submit" onclick="subOrder_Click" />
                                </div>
                   </div>

    </div>

</div>


</asp:Content>

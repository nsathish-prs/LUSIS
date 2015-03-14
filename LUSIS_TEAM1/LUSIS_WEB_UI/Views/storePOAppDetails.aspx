<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storePOAppDetails.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storePOAppDetails" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

    <div class="col-lg-8">
    
        <h1 class="page-header"> Purchase Order Details  </h1>

        <div class="col-lg-12">
        
            <div class="col-lg-3">Supplier Name :</div>
                        <div class="col-lg-4"><asp:Label ID="supName" runat="server" Text="Label"></asp:Label></div>
                        <div style="clear:both"></div>

            <div class="col-lg-3">Order ID</div>
                        <div class="col-lg-4"><asp:Label ID="lblOrder" runat="server" Text="Label"></asp:Label></div>
                        <div style="clear:both"></div>

            <div class="col-lg-3">Order Date</div>
                        <div class="col-lg-4"><asp:Label ID="lblOrderDate" runat="server" Text="Label"></asp:Label></div>
                        <div style="clear:both"></div>

            <div class="col-lg-3" style="margin-top:1.5%"><h4>Order Details</h4></div>

        </div>

        <div style="clear:both"></div>

        <div class="col-lg-12">
        <div class="table-responsive">


  
                    <asp:Table ID="TableRaiseReq" CssClass="table table-bordered table-hover table-striped" runat="server">
                        <asp:TableHeaderRow>
                            <asp:TableHeaderCell>Item ID</asp:TableHeaderCell>
                            <asp:TableHeaderCell>Item Description</asp:TableHeaderCell>
                            <asp:TableHeaderCell>Min. Order Quantity</asp:TableHeaderCell>
                            <asp:TableHeaderCell>Ordered Quantity</asp:TableHeaderCell>
                        </asp:TableHeaderRow>
                        <asp:TableRow>
                                        
                        </asp:TableRow>
                    </asp:Table>
                             
          </div>  

          </div>

          <div style="clear:both"></div>

            <div class="col-lg-2" style="padding-top:1.5%">
            
                  <asp:Button ID="btnApprove" runat="server" Text="Approve" 
                      class="btn btn-primary" type="submit" onclick="btnApprove_Click" />
                   

                <asp:Label ID="Label1" runat="server" Text="Label"></asp:Label>

            </div>

        </div>

    </div>

</div>

</asp:Content>

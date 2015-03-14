<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storePOList.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storePOList" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">
     <div class="col-lg-6">

            <h1 class="page-header">
                 Purchase Orders Placed
             </h1>

             <div class="panel panel-default">
                        
                <div class="table-responsive">
  
                    <asp:Table ID="TableRaiseReq" CssClass="table table-bordered table-hover table-striped" runat="server">
                        <asp:TableHeaderRow>
                            <asp:TableHeaderCell>Order No</asp:TableHeaderCell>
                            <%--<asp:TableHeaderCell>Total Items</asp:TableHeaderCell>--%>
                            <asp:TableHeaderCell>Expected Delivery</asp:TableHeaderCell>
                            <asp:TableHeaderCell>Approval Status</asp:TableHeaderCell>
                            <%--<asp:TableHeaderCell>Delivery Status</asp:TableHeaderCell>--%>
                        </asp:TableHeaderRow>
                        <asp:TableRow>
                                        
                        </asp:TableRow>
                    </asp:Table>
                             
                 </div>  
                            
              </div>
     </div>
</div>

</asp:Content>

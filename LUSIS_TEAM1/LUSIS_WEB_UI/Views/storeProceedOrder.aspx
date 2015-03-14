<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storeProceedOrder.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storeProceedOrder" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


<div class="row">
     <div class="col-lg-8">

            <h1 class="page-header">
                 Select Suppliers
             </h1>

                    <div class="panel panel-default">
                        
                    <div class="table-responsive">
                      
                        <asp:Table ID="proceedOrder" CssClass="table table-bordered table-hover" runat="server">

                            <asp:TableHeaderRow>
                                <asp:TableHeaderCell>Item Code</asp:TableHeaderCell>
                                <asp:TableHeaderCell>Description</asp:TableHeaderCell>
                                <asp:TableHeaderCell>Min. Order</asp:TableHeaderCell>
                                <asp:TableHeaderCell>Required</asp:TableHeaderCell>
                                <asp:TableHeaderCell>Supplier</asp:TableHeaderCell>
                            </asp:TableHeaderRow>

                        </asp:Table>
                        </div>  
                            
                    </div>

                    <div style="margin-top:1%">
                               <div class="col-lg-4" style="padding-top:1.5%">
                                <asp:Button ID="genPOrder" runat="server" Text="Generate Purchase Orders" 
                                       class="btn btn-primary" type="submit" onclick="genPOrder_Click" />
                                </div>
                   </div>

    </div>

</div>

</asp:Content>

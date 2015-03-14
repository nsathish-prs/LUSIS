<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptDisbursementLists.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptDisbursementLists" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="row">
                    <div class="col-lg-8">
                        <h1 class="page-header">
                            Disbursement Lists
                            </h1>
                            <div class="col-lg-12">
                          <div class="table-responsive">
                                    <asp:Table ID="TableDisbList" CssClass="table table-bordered table-hover table-striped" runat="server">
                                        <asp:TableHeaderRow>
                                            <asp:TableHeaderCell>Disbursement ID</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Date</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Department</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Representative</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Status</asp:TableHeaderCell>
                                        </asp:TableHeaderRow>                                        
                                    </asp:Table>                             
                             </div>  
                               </div>
                    </div> 
                    </div>
                 

</asp:Content>

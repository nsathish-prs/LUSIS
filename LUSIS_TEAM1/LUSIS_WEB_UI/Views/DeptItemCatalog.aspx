<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="DeptItemCatalog.aspx.cs" Inherits="LUSIS_WEB_UI.Views.DeptItemCatalog" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

                <div class="row">
                    <div class="col-lg-8">
                        <h1 class="page-header">
                            Item Catalog
                        </h1>
                        <div class="col-lg-7" style="padding-top:1.5%; text-align: right; position:fixed; padding-bottom:1.5%">
                                <asp:Button ID="btnAddToList" runat="server" Text="Add to List" 
                                    class="btn btn-primary" type="submit" onclick="btnAddToList_Click" />
                        </div>
                        <div style="clear:both"></div>
                
                <div class="col-lg-10">
                        <div class="table-responsive">


                                     <asp:Table ID="TableCatalog" class="table table-bordered table-hover table-striped" runat="server">
                                        <asp:TableHeaderRow>
                                            <asp:TableHeaderCell>Item Category</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Item Description</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Unit</asp:TableHeaderCell>
                                            <asp:TableHeaderCell>Select</asp:TableHeaderCell>
                                        </asp:TableHeaderRow>                                       
                                     </asp:Table>
                                    
                             
                        </div>  
                               </div>
                    </div> 
                    </div>
                    <div class="row">
                </div>


</asp:Content>

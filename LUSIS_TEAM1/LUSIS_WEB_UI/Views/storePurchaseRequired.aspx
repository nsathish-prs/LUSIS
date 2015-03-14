<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="storePurchaseRequired.aspx.cs" Inherits="LUSIS_WEB_UI.Views.storePurchaseRequired" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">
     <div class="col-lg-8">

            <h1 class="page-header">
                 Purchase Required
             </h1>

             <div class="col-lg-12">
                        <div class="panel panel-default">
                    <div class="table-responsive">
  
                        <asp:GridView ID="GridView1" 
                            CssClass="table table-bordered table-hover table-striped" runat="server" 
                            AutoGenerateColumns="False">
                            <Columns>
                                <asp:BoundField AccessibleHeaderText="ItemCode" HeaderText="Item No" 
                                    DataField="ItemCode" />
                                <asp:BoundField AccessibleHeaderText="Description" HeaderText="Description" 
                                    DataField="Description" />
                                <asp:BoundField AccessibleHeaderText="CurrentQuantity" HeaderText="Available" 
                                    DataField="CurrentQuantity" />
                                <asp:BoundField AccessibleHeaderText="ReOrderLevel" 
                                    HeaderText="Reorder Level" DataField="ReOrderLevel" />
                            </Columns>
                        </asp:GridView>
                        
                     
                             
                        </div>  
                        </div>

                        <div class="col-lg-12">
                             <h4><asp:Label ID="wow" runat="server" ForeColor="White" Text="Label"></asp:Label></h4>
                             </div>
                             
                    </div>
                               <div class="col-lg-4" style="padding-top:1.5%; ">
                                <asp:Button ID="proceedOrder" runat="server" Text="Proceed to Order" 
                                       class="btn btn-primary" type="submit" onclick="proceedOrder_Click"  />
                                </div>
                                <div style="clear:both"></div>
    </div>
</div>
</asp:Content>

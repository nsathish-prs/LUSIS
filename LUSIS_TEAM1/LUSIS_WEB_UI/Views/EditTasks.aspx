<%@ Page Title="" Language="C#" MasterPageFile="../Master/MasterPage.Master" AutoEventWireup="true" CodeBehind="EditTasks.aspx.cs" Inherits="LUSIS_WEB_UI.Views.EditTasks" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="row">

<div class="col-lg-9">

<div class="col-lg-2">
</div>
<div class="col-lg-7">
    <div class="panel-body">
        
        <h4>Edit Tasks Scheduled:</h4>
        <div class="col-lg-3"></div>
        <div class="col-lg-4">
        <asp:Button ID="Button1" CssClass="btn btn-sm btn-primary" runat="server" Text="Edit" />
        <asp:Button ID="Button2" CssClass="btn btn-sm btn-primary" runat="server" Text="Remove" />
        </div>
        <div style="clear:both"></div>
                <asp:CheckBoxList ID="CheckBoxList1" CssClass="checkbox" runat="server">
                    <asp:ListItem Text="Check for the Inventory stock Levels"></asp:ListItem>
                    <asp:ListItem Text="Check for the New Requisition"></asp:ListItem>
                    <asp:ListItem Text="Check for the Discrepancy."></asp:ListItem>
                </asp:CheckBoxList>
                <div class="col-lg-12" style="margin-top:1.5%">
                <div class="col-lg-4">
                    <asp:TextBox ID="TextBox1" CssClass="form-control" runat="server"></asp:TextBox>
                </div>
                <div class="col-lg-1">
                 <asp:Button ID="Button3" CssClass="btn btn-xs btn-primary" runat="server" Text="Add" />
                </div>
                </div>

    </div>

    </div>

</div>

</div>

</asp:Content>

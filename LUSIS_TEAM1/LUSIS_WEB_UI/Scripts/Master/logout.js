//<script type="text/javascript">

    var clicked = false;
    function CheckBrowser() {
        if (clicked == false) {
            //Browser Closed
        }
        else {
            //Redirected
            clicked = false;
        }
    }

    function Logout() {
        if (clicked == false) //Browser is Closed
        {
            window.location = "../Login.aspx?X=0";
        }
    }

//</script>
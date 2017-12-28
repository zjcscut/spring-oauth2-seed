<#assign ctx=request.getContextPath()>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>approval</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12 well">
            <h3>授权页</h3>
            应用名 : <span>${Session.authorizationRequest.clientId!"Unknow Server Name"}</span>

            <form id='confirmationForm' name='confirmationForm' action="${ctx}/oauth/authorize" method='post'>
                <input name='user_oauth_approval' value='true' type='hidden'/>
                <span>授权项 : </span>
                <br>
            <#list scopeList as s>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;${s}</span>
            </#list>
                <br>
                <input name='authorize' value='授权' type='submit'/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
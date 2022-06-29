<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-floating">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <input type="text"
               value="<#if user??>${user.username}</#if>"
               class="form-control ${(usernameError??)?string('is-invalid', '')}"
               placeholder="Login"
               name="username"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
    </div>
    <div class="form-floating">
        <label class="col-sm-2 col-form-label">Password:</label>
        <input type="password"
               name="password"
               class="form-control ${(passwordError??)?string('is-invalid', '')}"
               placeholder="Password" />
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <#if isRegisterForm>
    <div class="form-floating">
        <label class="col-sm-2 col-form-label">Password:</label>
        <input type="password2"
               name="password2"
               class="form-control ${(password2Error??)?string('is-invalid', '')}"
               placeholder="Retype password" />
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
    </div>
    <div class="form-floating">
        <label class="col-sm-2 col-form-label">Email:</label>
        <input type="email"
               class="form-control ${(emailError??)?string('is-invalid', '')}"
               placeholder="some@some.com"
               value="<#if user??>${user.email}</#if>"
               name="email"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
    </div>
    <div>
        <div class="g-recaptcha mt-3" data-sitekey="6LcU2GsgAAAAAHO_CtGs_sP46EBRvergHtTxVOsa"></div>
        <#if captchaError??>
            <div class="alert alert-danger" role="alert">
                ${captchaError}
            </div>
        </#if>
    </div>
    </#if>
    <div class="mr-2">
        <#if !isRegisterForm><a href="/registration">Add new User</a></#if>
    </div>
    <button type="submit" class="btn btn-primary mt-3"><#if isRegisterForm>Create<#else>Log in</#if></button>
</form>
</#macro>

<#macro logout>
<#include "security.ftl">
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button class="btn btn-primary" type="submit"><#if user??>Log Out<#else>Log in</#if></button>
</form>
</#macro>

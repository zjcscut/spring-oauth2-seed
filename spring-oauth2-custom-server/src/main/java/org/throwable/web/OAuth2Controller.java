package org.throwable.web;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/12/26 14:50
 */
@Controller
@SessionAttributes({"authorizationRequest"})
public class OAuth2Controller {

    @RequestMapping(value = "oauth/custom-approval-page")
    public String getAccessConfirmation(Map<String, Object> param, Model model, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Map<String, String> scopes = (Map<String, String>) (param.containsKey("scopes") ? param.get("scopes") :
                request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<>();
        scopeList.addAll(scopes.keySet());
        model.addAttribute("scopeList", scopeList);
        return "oauth_approval";
    }

    @RequestMapping({ "/oauth/custom-error-page" })
    public String handleError(Map<String, Object> model, HttpServletRequest request) {
        Object error = request.getAttribute("error");
        String errorSummary;
        if (error instanceof OAuth2Exception) {
            OAuth2Exception oauthError = (OAuth2Exception) error;
            errorSummary = HtmlUtils.htmlEscape(oauthError.getSummary());
        } else {
            errorSummary = "Unknown error";
        }
        model.put("errorSummary", errorSummary);
        return "oauth_error";
    }
}

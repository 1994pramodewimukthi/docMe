package edu.esoft.finalproject.DocMe.config;

import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.service.MCGDocumentUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class ServiceInterceptor implements HandlerInterceptor {
    @Autowired
    private MCGDocumentUploadService mcgDocumentUploadService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        User user = (User) request.getSession().getAttribute(AppConstant.USER);

        boolean mcgSkip = false;
        boolean isCheck = false;


        if ((null == session.getAttribute(AppConstant.MCG_SKIP))) {
            mcgSkip = false;
        } else {
            mcgSkip = (boolean) session.getAttribute(AppConstant.MCG_SKIP);
        }


        if (null != user
                && !(uri.endsWith(AppURL.MCG_VIEW_PDF)
                || uri.endsWith(AppURL.MCG_UPLOAD_NEW_SIGNATURE)
                || uri.endsWith(AppURL.MCG_UPLOAD_SIGN_PDF)
                || uri.endsWith(AppURL.MCG_SKIP_SIGN_PDF)
                || uri.endsWith(AppURL.LOGOUT)
                || uri.endsWith(".js")
                || uri.endsWith(".css")
        )) {
            try {
                isCheck = mcgDocumentUploadService.isMCGDocAvilable(user);
            } catch (Exception e) {

            }
        }


        if (user == null
                && !uri.replaceAll("/", "").equals(request.getContextPath().replaceAll("/", ""))
                && !uri.contains("login")
                && !uri.endsWith(".js")
                && !uri.endsWith(".css")
        ) {
            response.sendRedirect(request.getContextPath());
            return false;
        }

        if (isCheck && !mcgSkip) {
            response.sendRedirect(request.getContextPath().concat(AppURL.MCG_VIEW_AGREEMENT));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}

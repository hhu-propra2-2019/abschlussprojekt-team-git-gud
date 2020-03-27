package de.hhu.propra2.material2.mops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {

    /**
     * route to logout.
     *
     * @param request logout request
     * @return homepage routing
     * @throws Exception no handling
     */
    @GetMapping("/logout")
    public String logout(final HttpServletRequest request) throws Exception {
        request.logout();
        return "redirect:/material2/";
    }

}

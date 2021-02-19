package Projects.Example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/{id}")
    public String blog(@PathVariable(value = "id") long id, Model model) {

        String fullText;
        String author = new String();
        try {
            Post post = postRepository.findById(id).get();
            fullText = post.getFullText();
            author = post.getAuthor();
        }
        catch (Exception e) {
            fullText = "Ошибка запроса: " + String.valueOf(e);
        }
        model.addAttribute("fullText", fullText);
        model.addAttribute("author", author);
        return "page";
    }
//<img th:src="@{/images/background.jpg}">
}

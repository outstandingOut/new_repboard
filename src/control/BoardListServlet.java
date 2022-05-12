package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.RepBoardService;
import com.my.vo.RepBoard;

@WebServlet("/list")
public class BoardListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private RepBoardService service = new RepBoardService();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path ="/WEB-INF/views/error.jsp";
		request.setCharacterEncoding("utf-8");
		// 1. 요청전달데이터 얻기
		String word = request.getParameter("word");
		System.out.println(word);
		List<RepBoard> list;
		try {
			//2. 비즈니스로직 호출
			if(word == null) {
				list = service.findAll();
			}else {
				list = service.findByBoard_titleORBoard_writer(word);
			}
			// 3. 요청속성으로 호출결과값 추가
			request.setAttribute("list", list);
			path = "/WEB-INF/views/list.jsp";
		} catch (FindException e) {
			request.setAttribute("exception", e);
			e.printStackTrace();
		}
		// 4. view로 이동
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}

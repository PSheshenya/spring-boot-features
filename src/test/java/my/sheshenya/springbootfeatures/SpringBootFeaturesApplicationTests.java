package my.sheshenya.springbootfeatures;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.sheshenya.springbootfeatures.entity.User;
import my.sheshenya.springbootfeatures.entity.UserProjectRelation;
import my.sheshenya.springbootfeatures.model.ProjectDescription;
import my.sheshenya.springbootfeatures.model.UserProject;
import my.sheshenya.springbootfeatures.model.UserProjectDescriptions;
import my.sheshenya.springbootfeatures.service.DemoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringBootFeaturesApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DemoService demoService;

	@Test
	public void contextLoads() {
	}


	UserProjectDescriptions userProjectDescriptions;
	@Before
	public void initUserProjectDescriptions() throws MalformedURLException {
		userProjectDescriptions = new UserProjectDescriptions();
		userProjectDescriptions.setUserName("Alex");
		Set<ProjectDescription> projectDescriptions = Set.of(
				ProjectDescription.builder()
						.projectId(1L)
						.title("Project title one")
						.url(new URL("http://url.my/p1"))
						.build(),
				ProjectDescription.builder()
						.projectId(2L)
						.title("Project title two")
						.url(new URL("http://url.my/p2"))
						.build()
		);
		userProjectDescriptions.setProjectDescriptions(projectDescriptions);
	}

	UserProject userProject;
	@Before
	public void initUserProject() {
		userProject = new UserProject();
		userProject.setReadme("Reed me 1");
		userProject.setCommits(2);

		Set<User> projectContributors = Set.of(
				new User("Ivan", "Full name Iv"),
				new User("Max", "Full name Max")
		);
		userProject.setContributors(projectContributors);
	}


	@Test
	public void testGetUserProjectDescriptions_json_Success() throws Exception {
		String userAlexName = "Alex";

		given(this.demoService.getUserProjectDescriptions(userAlexName)).willReturn(userProjectDescriptions);

		this.mvc.perform( MockMvcRequestBuilders
				.get("/projects/" + userAlexName)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(userAlexName))
				.andExpect(MockMvcResultMatchers.jsonPath("$.projectDescriptions.length()").value(2));
	}

	@Test
	public void testGetUserProjectDescriptions_xml_Success() throws Exception {
		String userAlexName = "Alex";

		given(this.demoService.getUserProjectDescriptions(userAlexName)).willReturn(userProjectDescriptions);

		this.mvc.perform( MockMvcRequestBuilders
				.get("/projects/" + userAlexName)
				.accept(MediaType.APPLICATION_XML))
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.xpath("//userName").string(userAlexName))
				.andExpect(MockMvcResultMatchers.xpath("//projectDescriptions[projectId='1']/title").string("Project title one"))
				.andExpect(MockMvcResultMatchers.xpath("//projectDescriptions/projectId").nodeCount(2));
	}



	@Test
	public void testGetUserProject_json_Success() throws Exception {
		String userAlexName = "Alex";
		Long projectId = 1L;

		given(this.demoService.getUserProject(userAlexName, projectId)).willReturn(userProject);

		this.mvc.perform( MockMvcRequestBuilders
				.get("/projects/" + userAlexName + "/" + projectId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.readme").value("Reed me 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.commits").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.contributors.length()").value(2))
		;

	}


	@Test
	public void testGetUserProject_xml_Success() throws Exception {
		String userAlexName = "Alex";
		Long projectId = 1L;

		given(this.demoService.getUserProject(userAlexName, projectId)).willReturn(userProject);

		this.mvc.perform( MockMvcRequestBuilders
				.get("/projects/" + userAlexName + "/" + projectId)
				.accept(MediaType.APPLICATION_XML))
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.xpath("//readme").string("Reed me 1"))
				.andExpect(MockMvcResultMatchers.xpath("//commits").string("2"))
				.andExpect(MockMvcResultMatchers.xpath("//contributors/userName").nodeCount(2));
	}
}

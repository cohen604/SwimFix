package mainServer;

import DTO.*;
import DTO.AdminDTOs.SummaryDTO;
import DTO.CoachDTOs.InvitationResponseDTO;
import DTO.FeedbackDTOs.ConvertedVideoDTO;
import DTO.FeedbackDTOs.FeedbackVideoDTO;
import DTO.FeedbackDTOs.FeedbackVideoStreamer;
import DTO.ResearcherDTOs.FileDTO;
import DTO.ResearcherDTOs.FileDownloadDTO;
import DTO.ResearcherDTOs.ResearcherReportDTO;
import DTO.SwimmerDTOs.DateDTO;
import DTO.SwimmerDTOs.OpenTeamResponseDTO;
import DTO.SwimmerDTOs.SwimmerInvitationDTO;
import DTO.UserDTOs.UserDTO;
import DTO.UserDTOs.UserPermissionsDTO;
import Domain.StatisticsData.IStatistic;
import Domain.Streaming.*;
import Domain.Summaries.FeedbacksSummary;
import Domain.Summaries.UsersSummary;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.UserData.Interfaces.IInvitation;
import Domain.UserData.Interfaces.IUser;
import Domain.UserData.Invitation;
import Domain.UserData.SwimmerInvitation;
import DomainLogic.FileLoaders.ISkeletonsLoader;
import Storage.DbContext;
import mainServer.Providers.Interfaces.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class LogicManager {

    private IUserProvider _userProvider;
    private IFeedbackProvider _feedbackProvider;
    private ISkeletonsLoader _skeletonLoader;
    private IStatisticProvider _statisticProvider;
    private IReportProvider _reportProvider;
    private IEmailSenderProvider _emailSenderProvider;
    private IZipProvider _zipProvider;

    public LogicManager(IUserProvider userProvider,
                        IFeedbackProvider streamProvider,
                        ISkeletonsLoader skeletonLoader,
                        IStatisticProvider statisticProvider,
                        IReportProvider reportProvider,
                        IEmailSenderProvider emailSenderProvider,
                        IZipProvider zipProvider,
                        String dbName) {
        _userProvider = userProvider;
        _feedbackProvider = streamProvider;
        _skeletonLoader = skeletonLoader;
        _statisticProvider = statisticProvider;
        _reportProvider = reportProvider;
        _emailSenderProvider = emailSenderProvider;
        _zipProvider = zipProvider;
        // initialize server
        initializeServer(dbName);
    }

    private void initializeServer(String dbName) {
        // create system dirs
        createClientsDir();
        // create db
        DbContext dbContext = new DbContext(dbName);
        dbContext.initialize();
        // reload all users
        _userProvider.reload();
        // verify system administrator is created
        UserDTO swimAnalyticsUser = new UserDTO(
            "sIuzq2wFIHV4V335bf8o0QP3XQJ2",
            "swimfixofficial@gmail.com",
            "swim fix"
        );
        _userProvider.addSwimAnalyticsUser(swimAnalyticsUser);
    }

    /**
     * create the client directory in the server memory
     */
    private void createClientsDir() {
        try {
            Path path = Paths.get("clients");
            // TODO - check if good
            if (!Files.isDirectory(path)) {
                Files.createDirectory(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete all the files in the given path
     * @param file = the file to delete
     * @throws IOException - if fail
     */
    void delete(File file) throws IOException {
        if (file.isDirectory()) {
            FileUtils.deleteDirectory(file);
        }
        if (!file.delete())
            throw new FileNotFoundException("Failed to delete file: " + file);
    }

    /**
     * The function handle login of swimmers to the system
     * @param userDTO the swimmer information
     * @return true
     */
    public ActionResult<UserDTO> login(UserDTO userDTO) {
        if(_userProvider.login(userDTO)) {
            return new ActionResult<>(Response.SUCCESS, userDTO);
        }
        return new ActionResult<>(Response.FAIL,null);
    }

    /***
     * The function handle logout of swimmers from the system
     * @param user the swimmer information
     * @return
     */
    public ActionResult<Boolean> logout(UserDTO user) {
        if(_userProvider.logout(user)) {
            return new ActionResult<>(Response.SUCCESS, true);
        }
        return new ActionResult<>(Response.FAIL,null);
    }

    /***
     * The function return the users permissions
     * @param userDTO - the user
     * @return the user permissions if the user registered
     */
    public ActionResult<UserPermissionsDTO> getPermissions(UserDTO userDTO) {
        try {
            IUser user = _userProvider.getUser(userDTO);
            if (user != null && user.isLogged()) {
                UserPermissionsDTO permissionsDTO = new UserPermissionsDTO(
                    user.isSwimmer(),
                    user.isCoach(),
                    user.isAdmin(),
                    user.isResearcher()
                );
                return new ActionResult<>(Response.SUCCESS, permissionsDTO);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function handle upload video that want a streaming result
     * @param convertedVideoDTO the video we want to view
     * @return the streaming path for the feedback video
     * @ pre condition - the feedback video we are generating doesn't exits!
     */
    public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(UserDTO userDTO, ConvertedVideoDTO convertedVideoDTO) {
        try {
            IUser user = _userProvider.getUser(userDTO);
            if (user != null) {
                // create video
                List<String> detectorsNames = new LinkedList<>();
                IFeedbackVideo feedbackVideo = _feedbackProvider.generateFeedbackVideo(
                        convertedVideoDTO, user.getVideosPath(),
                        user.getFeedbacksPath(), user.getSkeletonsPath(), user.getMLSkeletonsPath(), detectorsNames);
                if (feedbackVideo != null && _userProvider.addFeedbackToUser(user, feedbackVideo)) {
                    FeedbackVideoStreamer feedbackVideoStreamer = feedbackVideo.generateFeedbackStreamer();
                    if (feedbackVideoStreamer != null) {
                        return new ActionResult<>(Response.SUCCESS, feedbackVideoStreamer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO what to return when fail
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function handle a streaming file request
     * @param path the path tp the file
     * @return the bytes for the file
     */
    public ActionResult<FeedbackVideoDTO> streamFile(String path) {
        //TODO need here to be access check
        try {
            FeedbackVideoDTO output = _feedbackProvider.streamFeedback(path);
            return new ActionResult<>(Response.SUCCESS, output);
        } catch (Exception e) {
            //TODO return here error
            System.out.println(e.getMessage());
        }
        //TODO return error
        //TODO maybe always generate a video if it a error video then return error video ?
        return null;
    }

    /**
     * The function return a researcher report from the given video and labels
     * @param userDTO - the user information
     * @param videoDTO - the video to created the analyze
     * @param fileDTO - the labels file
     * @return
     */
    public ActionResult<ResearcherReportDTO> getResearcherReport(UserDTO userDTO, ConvertedVideoDTO videoDTO, FileDTO fileDTO) {
        IUser user = _userProvider.getUser(userDTO);
        try {
            if (user != null && user.isLogged() && user.isResearcher()) {
                // create video
                List<String> detectorsNames = new LinkedList<>();
                IFeedbackVideo feedbackVideo = _feedbackProvider.generateFeedbackVideo(
                        videoDTO, user.getVideosPath(),
                        user.getFeedbacksPath(), user.getSkeletonsPath(), user.getMLSkeletonsPath(), detectorsNames);
                if (feedbackVideo != null) {
                    // add a graph to the file
                    List<ISwimmingSkeleton> raw = null;
                    if(fileDTO != null) {
                        raw = _skeletonLoader.read(fileDTO.getBytes());
                    }
                    List<ISwimmingSkeleton> model = _skeletonLoader.read(feedbackVideo.getMLSkeletonsPath());
                    List<ISwimmingSkeleton> modelAndInterpolation = feedbackVideo.getSwimmingSkeletons();
                    IStatistic statistic = _statisticProvider.analyze(raw, model, modelAndInterpolation);
                    String pdfPath = _reportProvider.generateReport(
                            raw,
                            model,
                            modelAndInterpolation,
                            user.getReportsPath(),
                            statistic,
                            feedbackVideo.getSwimmingPeriodTime(),
                            feedbackVideo.getSwimmingErrors());
                    ResearcherReportDTO reportDTO = new ResearcherReportDTO(
                            feedbackVideo.getPath(),
                            feedbackVideo.getSkeletonsPath(),
                            pdfPath);
                    return new ActionResult<>(Response.SUCCESS, reportDTO);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /***
     * get the days the swimmer swim
     * @param userDto - userdto
     * @return - list of days
     */
    public ActionResult<List<DateDTO>> getSwimmerHistoryDays(UserDTO userDto) {
        IUser user = _userProvider.getUser(userDto);
        if(user != null
            && user.isLogged()
            && user.isSwimmer()) {
            try {
                Collection<LocalDateTime> days = user.getFeedbacksDays();
                List<DateDTO> outputDays = new LinkedList<>();
                for(LocalDateTime day: days) {
                    outputDays.add(new DateDTO(
                            day.getYear(),
                            day.getMonthValue(),
                            day.getDayOfMonth()));
                }
                return new ActionResult<>(Response.SUCCESS, outputDays);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<>(Response.FAIL, null);
    }


    /**
     * get the hours of a swimmer by day
     * @param userDto - userdto
     * @return - list of feedbacks of the given date
     */
    public ActionResult<List<FeedbackVideoStreamer>> getSwimmerHistoryPoolsByDay(UserDTO userDto, DateDTO dateDTO) {
        IUser user = _userProvider.getUser(userDto);
        if(user != null
                && user.isLogged()
                && user.isSwimmer()) {
            try {
                LocalDateTime date = LocalDateTime.of(
                        dateDTO.getYear(),
                        dateDTO.getMonth(),
                        dateDTO.getDay(), 0, 0);
                Collection<IFeedbackVideo> feedbacks = user.getFeedbacksOfDay(date);
                List<FeedbackVideoStreamer> output = new LinkedList<>();
                if(feedbacks!=null && !feedbacks.isEmpty()) {
                    for (IFeedbackVideo feedbackVideo : feedbacks) {
                        output.add(new FeedbackVideoStreamer(feedbackVideo.getPath()));
                    }
                }
                return new ActionResult<>(Response.SUCCESS, output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /***
     * The function send an invitation email
     * @param userDTO - the user who want to send the email
     * @param to - the email to send the invitation
     * @return true if the email send, other wise false
     */
    public ActionResult<InvitationResponseDTO> invite(UserDTO userDTO, String to) {
        IUser user = _userProvider.getUser(userDTO);
        try {
            if(user!=null
                    && user.isLogged()
                    && user.isCoach()
                    && !to.isEmpty()
                    && to.contains("@")) {
                IUser userToSendTo = _userProvider.findUser(to);
                InvitationResponseDTO responseDTO = new InvitationResponseDTO();
                if(userToSendTo!=null && _userProvider.sendInvitation(user, userToSendTo)) {
                    responseDTO.setSendInvitationToUser(true);
                }
                else if(_emailSenderProvider.sendInvitationEmail(user.getEmail(), to)) {
                    responseDTO.setSendEmailToUser(true);
                }
                return new ActionResult<>(Response.SUCCESS, responseDTO);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /***
     * The function return a download file
     * @param userDTO the user who want to download
     * @param root the root folder
     * @param email the email folder
     * @param folder the name of the inner folder
     * @param fileName the filer name want to download
     * @return the file to download
     */
    public ActionResult<FileDownloadDTO> downloadFile(UserDTO userDTO, String root, String email, String folder, String fileName) {
        IUser user = _userProvider.getUser(userDTO);
        try {
            if(user != null
                && user.isLogged()
                && user.isResearcher()) {
                String path = root + "\\" + email + "\\" + folder + "\\" + fileName;
                File file = new File(path);
                if (file.exists()) {
                    byte[] data = Files.readAllBytes(file.toPath());
                    FileDownloadDTO fileDownloadDTO = new FileDownloadDTO(file.getName(), file.getPath(), data);
                    return new ActionResult<>(Response.SUCCESS, fileDownloadDTO);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /***
     * The function return a zip file that contains the request files
     * @param userDTO - the request user
     * @param files - the files we want to access to download
     * @return a zip file to download that contains the list of wanted files
     */
    public ActionResult<FileDownloadDTO> downloadFilesAsZip(UserDTO userDTO, String[] files) {
        IUser user = _userProvider.getUser(userDTO);
        try {
            if(user != null
                && user.isLogged()
                && user.isResearcher()) {
                String path = user.getDownloadsPath();
                File zip = _zipProvider.createZip(path, files);
                if (zip.exists()) {
                    byte[] data = Files.readAllBytes(zip.toPath());
                    FileDownloadDTO fileDownloadDTO = new FileDownloadDTO(zip.getName(), zip.getPath(), data);
                    return new ActionResult<>(Response.SUCCESS, fileDownloadDTO);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }
    /***
     * delete a feedback of a user
     * @param userDTO - the user who own the feedback
     * @param dateDTO - the date of the feedback
     * @param path - the id of the feedback to delete
     * @return - true if deleted, false if not
     */
    public ActionResult<Boolean> deleteFeedbackByID(UserDTO userDTO, DateDTO dateDTO, String path) {
        IUser user = _userProvider.getUser(userDTO);
        try {
            if(user != null
                    && _userProvider.deleteFeedbackByID(user, path)) {
                return new ActionResult<>(Response.SUCCESS, true);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, false);
    }

    /***
     * The function return a list of users that they not admins
     * @param userDTO - the request user
     * @return List of users that not admins
     */
    public ActionResult<List<UserDTO>> findUsersThatNotAdmin(UserDTO userDTO) {
        IUser user = _userProvider.getUser(userDTO);
        try {
            if(user != null) {
                Collection<? extends IUser> users = _userProvider.findUsersThatNotAdmin(user);
                if(users!=null) {
                    List<UserDTO> output = new LinkedList<>();
                    for (IUser foundUser : users) {
                        UserDTO outputUser = new UserDTO(
                                foundUser.getUid(),
                                foundUser.getEmail(),
                                foundUser.getName());
                        output.add(outputUser);
                    }
                    return new ActionResult<>(Response.SUCCESS, output);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /***
     * The function return a list of users that they not researchers
     * @param userDTO - the request user
     * @return List of users that not researchers
     */
    public ActionResult<List<UserDTO>> findUsersThatNotResearcher(UserDTO userDTO) {
        IUser user = _userProvider.getUser(userDTO);
        try {
            if(user != null) {
                Collection<? extends IUser> users = _userProvider.findUsersThatNotResearcher(user);
                if(users!=null) {
                    List<UserDTO> output = new LinkedList<>();
                    for (IUser foundUser : users) {
                        UserDTO outputUser = new UserDTO(
                                foundUser.getUid(),
                                foundUser.getEmail(),
                                foundUser.getName());
                        output.add(outputUser);
                    }
                    return new ActionResult<>(Response.SUCCESS, output);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function add admin permissions to user
     * @param adminDTO the request user
     * @param addToUserDTO the user to add to
     * @return true if the user have admin permissions
     */
    public ActionResult<Boolean> addAdmin(UserDTO adminDTO, UserDTO addToUserDTO) {
        IUser admin = _userProvider.getUser(adminDTO);
        IUser userToAdd = _userProvider.getUser(addToUserDTO);
        try {
            if(admin != null) {
                boolean output = _userProvider.addAdmin(admin, userToAdd);
                return new ActionResult<>(Response.SUCCESS, output);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function add researcher to the desired user
     * @param adminDTO - the admin trying to add
     * @param addToUserDTO - the user to become a researcher
     * @return true if the user added as researcher, otherwise false
     */
    public ActionResult<Boolean> addResearcher(UserDTO adminDTO, UserDTO addToUserDTO) {
        IUser admin = _userProvider.getUser(adminDTO);
        IUser userToAdd = _userProvider.getUser(addToUserDTO);
        try {
            if(admin != null) {
                boolean output = _userProvider.addResearcher(admin, userToAdd);
                return new ActionResult<>(Response.SUCCESS, output);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function return a report off the system
     * @param adminDTO
     * @return
     */
    public ActionResult<SummaryDTO> getSummary(UserDTO adminDTO) {
        IUser admin = _userProvider.getUser(adminDTO);
        try {
            if(admin!=null
                    && admin.isAdmin()
                    && admin.isLogged()) {
                UsersSummary usersSummary = _userProvider.getSummary();
                FeedbacksSummary feedbacksSummary = _feedbackProvider.getSummary();
                SummaryDTO summaryDTO = new SummaryDTO(
                        usersSummary.getUsers(),
                        usersSummary.getLoggedUsers(),
                        usersSummary.getSwimmers(),
                        usersSummary.getLoggedSwimmers(),
                        usersSummary.getCoaches(),
                        usersSummary.getLoggedCoaches(),
                        usersSummary.getResearchers(),
                        usersSummary.getLoggedResearchers(),
                        usersSummary.getAdmins(),
                        usersSummary.getLoggedAdmins(),
                        feedbacksSummary.getFeedbacks()
                );
                return new ActionResult<>(Response.SUCCESS, summaryDTO);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /***
     * The function add coach to the given user
     * @param coachDTO - the user to add a new team to
     * @param teamName - the new team name
     * @return if the team added to the user otherwise false and the reason
     */
    public ActionResult<OpenTeamResponseDTO> addCoach(UserDTO coachDTO, String teamName) {
        IUser coach = _userProvider.getUser(coachDTO);
        try {
            if(coach!=null
                    && coach.isCoach()
                    && coach.isLogged()) {
                OpenTeamResponseDTO responseDTO = new OpenTeamResponseDTO(teamName);
                if(coach.isCoach()) {
                    responseDTO.setAlreadyCoach(true);
                    return new ActionResult<>(Response.FAIL, responseDTO);
                }
                else if(_userProvider.addCoach(coach, teamName)) {
                    responseDTO.setAdded(true);
                    return new ActionResult<>(Response.SUCCESS, responseDTO);
                }
                responseDTO.setAlreadyExists(true);
                return new ActionResult<>(Response.FAIL, responseDTO);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function return the pending invitations of a swimmer
     * @param userDTO - the user
     * @return list of the swimmer invitations
     */
    public ActionResult<List<SwimmerInvitationDTO>> getPendingInvitations(UserDTO userDTO) {
        IUser user = _userProvider.getUser(userDTO);
        try {
            if (user != null
                    && user.isLogged()
                    && user.isSwimmer()) {
                Collection<? extends IInvitation> collection = user.getInvitations();
                if(collection!=null) {
                    List<SwimmerInvitationDTO> output = convertToSwimmerInvitation(collection);
                    return new ActionResult<>(Response.SUCCESS, output);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function return the invitations history of a swimmer
     * @param userDTO - the user
     * @return list of the swimmer invitations history
     */
    public ActionResult<List<SwimmerInvitationDTO>> getInvitationsHistory(UserDTO userDTO) {
        IUser user = _userProvider.getUser(userDTO);
        try {
            if (user != null
                    && user.isLogged()
                    && user.isSwimmer()) {
                Collection<? extends IInvitation> collection = user.getInvitationsHistory();
                if(collection!=null) {
                    List<SwimmerInvitationDTO> output = convertToSwimmerInvitation(collection);
                    return new ActionResult<>(Response.SUCCESS, output);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function convert a collection of invitations to swimmer invitation dto
     * @param collection the collection to convert
     * @return the list of swimmer invitation dto
     */
    private List<SwimmerInvitationDTO> convertToSwimmerInvitation(Collection<? extends IInvitation> collection) {
        List<SwimmerInvitationDTO> output = new LinkedList<>();
        for (IInvitation invitation : collection) {
            SwimmerInvitationDTO swimmerInvitationDTO = new SwimmerInvitationDTO(
                    invitation.getTeamId(),
                    invitation.getCreationTime());
            output.add(swimmerInvitationDTO);
        }
        return output;
    }
}


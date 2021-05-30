package mainServer;

import DTO.*;
import DTO.AdminDTOs.SummaryDTO;
import DTO.CoachDTOs.CoachSwimmerFeedbackDTO;
import DTO.CoachDTOs.InvitationResponseDTO;
import DTO.CoachDTOs.TeamDTO;
import DTO.FeedbackDTOs.*;
import DTO.ResearcherDTOs.FileDTO;
import DTO.ResearcherDTOs.FileDownloadDTO;
import DTO.ResearcherDTOs.ResearcherReportDTO;
import DTO.SwimmerDTOs.*;
import DTO.UserDTOs.UserDTO;
import DTO.UserDTOs.UserPermissionsDTO;
import Domain.StatisticsData.IStatistic;
import Domain.Streaming.*;
import Domain.Summaries.FeedbacksSummary;
import Domain.Summaries.UsersSummary;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.UserData.Interfaces.IInvitation;
import Domain.UserData.Interfaces.ISwimmer;
import Domain.UserData.Interfaces.ITeam;
import Domain.UserData.Interfaces.IUser;
import DomainLogic.FileLoaders.ISkeletonsLoader;
import Storage.DbContext;
import mainServer.Providers.Interfaces.*;


import java.io.File;
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
            if (!Files.isDirectory(path)) {
                Files.createDirectory(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The function handle login of swimmers to the system
     * @param userDTO the swimmer information
     * @return true
     */
    public ActionResult<UserDTO> login(UserDTO userDTO) {
        try {
            if(_userProvider.login(userDTO)) {
                return new ActionResult<>(Response.SUCCESS, userDTO);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL,null);
    }

    /***
     * The function handle logout of swimmers from the system
     * @param user the swimmer information
     * @return
     */
    public ActionResult<Boolean> logout(UserDTO user) {
        try {
            if (_userProvider.logout(user)) {
                return new ActionResult<>(Response.SUCCESS, true);
            }
        }
        catch (Exception e){
            e.printStackTrace();
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
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function handle a streaming file request
     * @param path the path tp the file
     * @return the bytes for the file
     */
    public ActionResult<FeedbackVideoDTO> streamFile(String path) {
        try {
            FeedbackVideoDTO output = _feedbackProvider.streamFeedback(path);
            return new ActionResult<>(Response.SUCCESS, output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        try {
            IUser user = _userProvider.getUser(userDTO);
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
    public ActionResult<List<DateDayDTO>> getSwimmerHistoryDays(UserDTO userDto) {
        try {
            IUser user = _userProvider.getUser(userDto);
            if (user != null
                    && user.isLogged()
                    && user.isSwimmer()) {
                Collection<LocalDateTime> days = user.getFeedbacksDays();
                List<DateDayDTO> outputDays = new LinkedList<>();
                for (LocalDateTime day : days) {
                    outputDays.add(new DateDayDTO(
                            day.getYear(),
                            day.getMonthValue(),
                            day.getDayOfMonth()));
                }
                return new ActionResult<>(Response.SUCCESS, outputDays);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }


    /**
     * get the hours of a swimmer by day
     * @param userDto - userdto
     * @return - list of feedbacks of the given date
     */
    public ActionResult<List<SwimmerFeedbackDTO>> getSwimmerHistoryPoolsByDay(UserDTO userDto, DateDayDTO dateDayDTO) {
        try {
            IUser user = _userProvider.getUser(userDto);
            if (user != null
                    && user.isLogged()
                    && user.isSwimmer()) {
                LocalDateTime date = LocalDateTime.of(
                        dateDayDTO.getYear(),
                        dateDayDTO.getMonth(),
                        dateDayDTO.getDay(), 0, 0);
                Collection<IFeedbackVideo> feedbacks = user.getFeedbacksOfDay(date);
                List<SwimmerFeedbackDTO> output = new LinkedList<>();
                if (feedbacks != null && !feedbacks.isEmpty()) {
                    for (IFeedbackVideo feedbackVideo : feedbacks) {
                        output.add(
                                new SwimmerFeedbackDTO(
                                        feedbackVideo.getPath(),
                                        feedbackVideo.getDate()
                                )
                        );
                    }
                }
                return new ActionResult<>(Response.SUCCESS, output);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
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
        try {
            IUser user = _userProvider.getUser(userDTO);
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
        try {
            IUser user = _userProvider.getUser(userDTO);
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
        try {
            IUser user = _userProvider.getUser(userDTO);
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
     * @param dateDayDTO - the date of the feedback
     * @param path - the id of the feedback to delete
     * @return - true if deleted, false if not
     */
    public ActionResult<Boolean> deleteFeedbackByID(UserDTO userDTO, DateDayDTO dateDayDTO, String path) {
        try {
            IUser user = _userProvider.getUser(userDTO);
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
        try {
            IUser user = _userProvider.getUser(userDTO);
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
        try {
            IUser user = _userProvider.getUser(userDTO);
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
        try {
            IUser admin = _userProvider.getUser(adminDTO);
            IUser userToAdd = _userProvider.getUser(addToUserDTO);
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
        try {
            IUser admin = _userProvider.getUser(adminDTO);
            IUser userToAdd = _userProvider.getUser(addToUserDTO);
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
        try {
            IUser admin = _userProvider.getUser(adminDTO);
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
        try {
            IUser coach = _userProvider.getUser(coachDTO);
            if(coach!=null
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
        try {
            IUser user = _userProvider.getUser(userDTO);
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
        try {
            IUser user = _userProvider.getUser(userDTO);
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
                    invitation.getId(),
                    invitation.getTeamId(),
                    invitation.getSwimmerId(),
                    invitation.getCreationTime(),
                    invitation.isPending(),
                    invitation.isApprove(),
                    invitation.isDenied()
                    );
            output.add(swimmerInvitationDTO);
        }
        return output;
    }

    /**
     * The function make swimmer approve invitation
     * @param userDTO - user
     * @param invitationId - the invitation id
     * @return true if the invitation is approved, otherwise false
     */
    public ActionResult<Boolean> approveInvitation(UserDTO userDTO, String invitationId) {
        try {
            IUser user = _userProvider.getUser(userDTO);
            if(user!=null
                    && user.isSwimmer()
                    && user.isLogged()) {
                boolean approved = _userProvider.approveInvitation(user, invitationId);
                if(approved) {
                    return new ActionResult<>(Response.SUCCESS, true);
                }
                return new ActionResult<>(Response.FAIL, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function make swimmer deny invitation
     * @param userDTO - user
     * @param invitationId - the invitation id
     * @return true if the invitation is denied, otherwise false
     */
    public ActionResult<Boolean> denyInvitation(UserDTO userDTO, String invitationId) {
        try {
            IUser user = _userProvider.getUser(userDTO);
            if(user!=null
                    && user.isSwimmer()
                    && user.isLogged()) {
                boolean approved = _userProvider.denyInvitation(user, invitationId);
                if(approved) {
                    return new ActionResult<>(Response.SUCCESS, true);
                }
                return new ActionResult<>(Response.FAIL, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /***
     * The function take swimmer and remove him from his team
     * @param userDTO - swimmer
     * @param teamId - the teamId
     * @return true if swimmer left the team, otherwise false
      */
    public ActionResult<Boolean> leaveTeam(UserDTO userDTO, String teamId) {
        try {
            IUser user = _userProvider.getUser(userDTO);
            if(user!=null) {
                boolean removed = _userProvider.leaveTeam(user, teamId);
                if(removed) {
                    return new ActionResult<>(Response.SUCCESS, true);
                }
                return new ActionResult<>(Response.SUCCESS, false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function return the team name of the swimmer
     * @param userDTO - the swimmer
     * @return the team name.
     */
    public ActionResult<MyTeamDTO> getMyTeam(UserDTO userDTO) {
        try {
            IUser user = _userProvider.getUser(userDTO);
            if(user!=null) {
                String teamName = _userProvider.getMyTeam(user);
                if(teamName!=null) {
                    return new ActionResult<>(Response.SUCCESS, new MyTeamDTO(true, teamName));
                }
                return new ActionResult<>(Response.SUCCESS, new MyTeamDTO(false, ""));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function return the coach team
     * @param userDTO - the coach
     * @return the coach team
     */
    public ActionResult<TeamDTO> getCoachTeam(UserDTO userDTO) {
        try {
            IUser iUser = _userProvider.getUser(userDTO);
            if(iUser!=null) {
                ITeam iTeam = _userProvider.getCoachTeam(iUser);
                if(iTeam != null) {
                    List<SwimmerDTO> swimmers = new LinkedList<>();
                    for(ISwimmer iSwimmer : iTeam.getSwimmersCollection()) {
                        SwimmerDTO swimmer = new SwimmerDTO(
                            iSwimmer.getEmail(),
                            iSwimmer.getNumberOfFeedbacks()
                        );
                        swimmers.add(swimmer);
                    }
                    List<SwimmerInvitationDTO> invitations = new LinkedList<>();
                    for(IInvitation iInvitation: iTeam.getInvitationsCollection()){
                        SwimmerInvitationDTO invitation = new SwimmerInvitationDTO(
                                iInvitation.getId(),
                                iInvitation.getTeamId(),
                                iInvitation.getSwimmerId(),
                                iInvitation.getCreationTime(),
                                iInvitation.isPending(),
                                iInvitation.isApprove(),
                                iInvitation.isDenied()
                        );
                        invitations.add(invitation);
                    }
                    String teamName = iTeam.getName();
                    TeamDTO teamDto = new TeamDTO(
                            teamName,
                            new DateDayDTO(iTeam.getOpenDate()),
                            iTeam.getCoachId(),
                            swimmers,
                            invitations
                    );
                    return new ActionResult<>(Response.SUCCESS, teamDto);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function return the swimmers feedbacks for the coach request
     * @param coachDto - the coach
     * @param swimmerEmail - the swimmer
     * @return the list of feedbacks of the swimmer
     */
    public ActionResult<List<CoachSwimmerFeedbackDTO>> coachGetSwimmerFeedbacks(UserDTO coachDto, String swimmerEmail) {
        try {
            IUser coach = _userProvider.getUser(coachDto);
            IUser swimmer = _userProvider.findUser(swimmerEmail);
            if(coach!=null && swimmer!=null) {
                Set<Map.Entry<String, IFeedbackVideo>> feedbacks = _userProvider.coachGetFeedbacks(coach, swimmer);
                List<CoachSwimmerFeedbackDTO> output = new LinkedList<>();
                for(Map.Entry<String, IFeedbackVideo> entry: feedbacks) {
                    IFeedbackVideo iFeedbackVideo = entry.getValue();
                    CoachSwimmerFeedbackDTO dto = new CoachSwimmerFeedbackDTO(
                            swimmer.getEmail(),
                            iFeedbackVideo.getDate(),
                            iFeedbackVideo.getPath(),
                            entry.getKey(),
                            iFeedbackVideo.getNumberOfErrors(),
                            iFeedbackVideo.getNumberOfComments()
                    );
                    output.add(dto);
                }
                return new ActionResult<>(Response.SUCCESS, output);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function return the swimmer feedback data for coach request
     * @param userDTO - the coach
     * @param swimmerEmail - the swimmer
     * @param feedbackKey - the feedback key
     * @return
     */
    public ActionResult<FeedbackDataDTO> coachGetSwimmerFeedback(UserDTO userDTO, String swimmerEmail, String feedbackKey) {
        try {
            IUser iCoach = _userProvider.getUser(userDTO);
            IUser iSwimmer = _userProvider.findUser(swimmerEmail);
            if(iCoach != null
                    && iSwimmer!=null) {
                IFeedbackVideo feedbackVideo = _userProvider.coachGetSwimmerFeedback(iCoach, iSwimmer, feedbackKey);
                if(feedbackVideo !=null) {
                    List<TextualCommentDTO> comments = new LinkedList<>();
                    for(ITextualComment textualComment: feedbackVideo.getComments()) {
                        comments.add(new TextualCommentDTO(
                            textualComment.getDate(),
                            textualComment.getCoachId(),
                            textualComment.getText()
                        ));
                    }
                    FeedbackDataDTO feedbackDataDTO = new FeedbackDataDTO(
                            swimmerEmail,
                            feedbackVideo.getPath(),
                            feedbackKey,
                            feedbackVideo.getDate(),
                            comments
                    );
                    return new ActionResult<>(Response.SUCCESS, feedbackDataDTO);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ActionResult<>(Response.FAIL, null);
    }
}


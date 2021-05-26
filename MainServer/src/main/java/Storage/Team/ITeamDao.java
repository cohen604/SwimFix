package Storage.Team;

import Domain.UserData.Team;

public interface ITeamDao {

    Team find(String teamId);

    boolean isTeamExists(String teamId);

}

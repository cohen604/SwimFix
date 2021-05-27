package Storage.Team;

import Domain.UserData.Team;

public interface ITeamDao {

    Team find(String teamId);

    Team tryInsertThenUpdate(Team team);

    boolean isTeamExists(String teamId);

    Team update(Team value);
}

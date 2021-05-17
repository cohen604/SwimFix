package Storage.Researcher;

import Domain.UserData.Researcher;

public interface IResearcherDao {

    Researcher update(Researcher value);

    Researcher tryInsertThenUpdate(Researcher researcher);
}

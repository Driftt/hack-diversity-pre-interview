package com.drift.interview.dao;

import com.drift.interview.model.TeamMember;
import com.google.common.base.Optional;
import com.hubspot.rosetta.jdbi.BindWithRosetta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public abstract class TeamMemberDAO {
  @SqlUpdate("TRUNCATE TABLE `team`")
  public abstract void reset();

  @SqlUpdate("CREATE TABLE IF NOT EXISTS `team` " +
      "(id long primary key, name varchar(255) NULL, email varchar(255), avatar varchar(255) NULL)")
  public abstract void setup();

  @SqlUpdate("INSERT INTO `team` (id, name, email, avatar) VALUES (:id, :name, :email, :avatar)")
  public abstract int create(@BindWithRosetta TeamMember teamMember);

  @Mapper(TeamMemberMapper.class)
  @SingleValueResult
  @SqlQuery("SELECT * FROM `team` WHERE id = :id")
  public abstract Optional<TeamMember> find(@Bind("id") long id);

  public static class TeamMemberMapper implements ResultSetMapper<TeamMember> {

    @Override
    public TeamMember map(int index, ResultSet resultSet, StatementContext context) throws SQLException {
      TeamMember.Builder teamMember = TeamMember.builder()
          .setId(resultSet.getLong("id"))
          .setEmail(resultSet.getString("email"))
          .setAvatar(resultSet.getString("avatar"))
          .setName(resultSet.getString("name"));
      return teamMember.build();
    }
  }
}

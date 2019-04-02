package com.drift.interview.dao;

import com.drift.interview.model.Conversation;
import com.google.common.base.Optional;
import com.hubspot.rosetta.jdbi.BindWithRosetta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public abstract class ConversationDAO {

  @SqlUpdate("TRUNCATE TABLE `conversation`")
  public abstract void reset();

  @SqlUpdate("CREATE TABLE IF NOT EXISTS `conversation` (id long primary key)")
  public abstract void setup();

  @SqlUpdate("INSERT INTO `conversation` (id) VALUES (:id)")
  public abstract int create(@BindWithRosetta Conversation conversation);

  @Mapper(ConversationMapper.class)
  @SingleValueResult
  @SqlQuery("SELECT * FROM `conversation` where id = :id")
  public abstract Optional<Conversation> find(@Bind("id") long id);

  @Mapper(ConversationMapper.class)
  @SqlQuery("SELECT * FROM `conversation`")
  public abstract List<Conversation> findAll();

  @Mapper(ConversationMapper.class)
  @SqlQuery("SELECT * FROM `conversation` LIMIT :limit")
  public abstract List<Conversation> findAll(@Bind("limit") int limit);

  public static class ConversationMapper implements ResultSetMapper<Conversation> {

    @Override
    public Conversation map(int index, ResultSet resultSet, StatementContext context) throws SQLException {
      Conversation.Builder convo = Conversation.builder()
          .setId(resultSet.getLong("id"));
      return convo.build();
    }
  }
}

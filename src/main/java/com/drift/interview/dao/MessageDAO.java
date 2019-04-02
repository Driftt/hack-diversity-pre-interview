package com.drift.interview.dao;

import com.drift.interview.model.Message;
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

public abstract class MessageDAO {

  @SqlUpdate("TRUNCATE TABLE `message`")
  public abstract void reset();

  @SqlUpdate("CREATE TABLE IF NOT EXISTS `message` " +
      "(id long primary key, conversationId long, created_at long, author_id long, is_team_member boolean default false, message varchar(255))")
  public abstract void setup();

  @SqlUpdate("INSERT INTO `message` (id, conversationId, created_at, author_id, is_team_member, message) " +
      "VALUES (:id, :conversationId, :createdAt, :authorId, :teamMember, :message)")
  public abstract int create(@BindWithRosetta Message message);

  @Mapper(MessageMapper.class)
  @SingleValueResult
  @SqlQuery("SELECT * FROM `message` WHERE id = :id")
  public abstract Optional<Message> find(@Bind("id") long id);

  @Mapper(MessageMapper.class)
  @SqlQuery("SELECT * FROM `message` WHERE conversationId= :conversationId")
  public abstract List<Message> findByConversationId(@Bind("conversationId") long conversationId);

  @Mapper(MessageMapper.class)
  @SqlQuery("SELECT * FROM `message` LIMIT :limit")
  public abstract List<Message> findAll(@Bind("limit") int limit);

  public static class MessageMapper implements ResultSetMapper<Message> {

    @Override
    public Message map(int index, ResultSet resultSet, StatementContext context) throws SQLException {
      Message.Builder msg = Message.builder()
          .setConversationId(resultSet.getLong("conversationId"))
          .setId(resultSet.getLong("id"))
          .setTeamMember(resultSet.getBoolean("is_team_member"))
          .setMessage(resultSet.getString("message"))
          .setAuthorId(resultSet.getLong("author_id"))
          .setCreatedAt(resultSet.getLong("created_at"));
      return msg.build();
    }
  }
}

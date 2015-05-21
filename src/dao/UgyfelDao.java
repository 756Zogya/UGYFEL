package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.data.Dokumentum;
import dao.data.Jogi;
import dao.data.Termeszetes;
import dao.data.Type;
import dao.data.Ugyfel;

public class UgyfelDao {

	private final String SQL_NEXT_ID = "select nextval('seq_ugyfel') as nextId";
	private final String SQL_INSERT_UGYFEL = "insert into \"UGYFEL\" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String SQL_INSERT_DOKUMENTUM = "insert into \"DOKUMENTUM\" values (?, ?, ?, ?, ?)";
	private final String SQL_SELECT_UGYFEL = "select * from \"UGYFEL\" where id = ?";
	private final String SQL_SELECT_DOKUMENTUM = "select * from \"DOKUMENTUM\" where ugyfel_id = ?";
	private final String SQL_SELECT_DOKUMENTUM_ISSUE_DATE = "select * from \"DOKUMENTUM\" where issue_date < ?";

	public long saveUgyfel(Ugyfel ugyfel) throws UgyfelDaoException {

		if (ugyfel.getDocuments() == null || ugyfel.getDocuments().isEmpty()) {
			throw new UgyfelDaoException("Ducuments are missing!");
		}

		Connection connection = null;
		PreparedStatement psSelect = null;
		PreparedStatement psInsertUgyfel = null;
		PreparedStatement psInsertDok = null;
		ResultSet rs = null;
		long id = 0;
		try {
			connection = ConnectionFactory.getPostgreConncection();
			connection.setAutoCommit(false);

			psSelect = connection.prepareStatement(SQL_NEXT_ID);
			rs = psSelect.executeQuery();

			while (rs.next()) {
				id = rs.getLong("nextId");
				break;
			}

			psInsertUgyfel = connection.prepareStatement(SQL_INSERT_UGYFEL);
			psInsertUgyfel.setLong(1, id);
			psInsertUgyfel.setBoolean(2, ugyfel.isCustomer());
			psInsertUgyfel.setDate(3, new Date(ugyfel.getLastIdentified()
					.getTime()));

			if (ugyfel instanceof Termeszetes) {
				Termeszetes termeszetes = (Termeszetes) ugyfel;

				psInsertUgyfel.setString(4, termeszetes.getLastName());
				psInsertUgyfel.setString(5, termeszetes.getFirstName());
				psInsertUgyfel.setDate(6, new Date(termeszetes.getBirthDate()
						.getTime()));
				psInsertUgyfel.setString(7, null);
				psInsertUgyfel.setString(8, null);
				psInsertUgyfel.setDate(9, null);
				psInsertUgyfel.setInt(10, 1);
			} else if (ugyfel instanceof Jogi) {
				Jogi jogi = (Jogi) ugyfel;

				psInsertUgyfel.setString(4, null);
				psInsertUgyfel.setString(5, null);
				psInsertUgyfel.setDate(6, null);
				psInsertUgyfel.setString(7, jogi.getName());
				psInsertUgyfel.setString(8, jogi.getShortName());
				psInsertUgyfel.setDate(9, new Date(jogi.getEstablishmentDate()
						.getTime()));
				psInsertUgyfel.setInt(10, 2);
			}

			psInsertUgyfel.executeUpdate();
			connection.commit();

			for (Dokumentum dokumentum : ugyfel.getDocuments()) {
				psInsertDok = connection
						.prepareStatement(SQL_INSERT_DOKUMENTUM);
				psInsertDok.setLong(1, id);
				psInsertDok.setString(2, dokumentum.getNumber());
				psInsertDok.setDate(3, new Date(dokumentum.getIssueDate()
						.getTime()));
				Type type = dokumentum.getType();
				if (type != null) {
					psInsertDok.setString(4, type.getCode());
					psInsertDok.setBoolean(5, type.isUnique());
				} else {
					psInsertDok.setString(4, null);
					psInsertDok.setBoolean(5, false);
				}
				psInsertDok.executeUpdate();
			}

			connection.commit();

		} catch (SQLException e) {
			String errorMessage = "Unable to insert new ugyfel!";
			throw new UgyfelDaoException(errorMessage, e.getCause());
		} finally {
			closeResultSet(rs);
			closePreparedStatement(psSelect);
			closePreparedStatement(psInsertUgyfel);
			closePreparedStatement(psInsertDok);
			closeConnection(connection);
		}
		return id;
	}

	public Ugyfel getUgyfel(long id) throws UgyfelDaoException {

		Connection connection = null;
		PreparedStatement psSelectUgyfel = null;
		ResultSet rsUgyfel = null;
		PreparedStatement psSelectDok = null;
		ResultSet rsDok = null;
		Termeszetes termeszetes = null;
		Jogi jogi = null;
		try {
			connection = ConnectionFactory.getPostgreConncection();

			psSelectUgyfel = connection.prepareStatement(SQL_SELECT_UGYFEL);
			psSelectUgyfel.setLong(1, id);
			rsUgyfel = psSelectUgyfel.executeQuery();

			psSelectDok = connection.prepareStatement(SQL_SELECT_DOKUMENTUM);
			psSelectDok.setLong(1, id);
			rsDok = psSelectDok.executeQuery();

			List<Dokumentum> documents = new ArrayList<>();
			while (rsDok.next()) {
				Dokumentum dokumentum = new Dokumentum();
				dokumentum.setNumber(rsDok.getString("dok_number"));
				dokumentum.setIssueDate(new Date(rsDok.getDate("issue_date")
						.getTime()));
				Type type = new Type();
				type.setCode(rsDok.getString("code"));
				type.setUnique(rsDok.getBoolean("dok_unique"));
				dokumentum.setType(type);
				documents.add(dokumentum);
			}

			while (rsUgyfel.next()) {
				if (rsUgyfel.getInt("type") == 1) {
					termeszetes = new Termeszetes();
					termeszetes.setId(rsUgyfel.getLong("id"));
					termeszetes.setCustomer(rsUgyfel.getBoolean("customer"));
					termeszetes.setLastIdentified(new Date(rsUgyfel.getDate(
							"last_identified").getTime()));
					termeszetes.setLastName(rsUgyfel.getString("last_name"));
					termeszetes.setFirstName(rsUgyfel.getString("first_name"));
					termeszetes.setBirthDate(new Date(rsUgyfel.getDate(
							"birth_date").getTime()));
					termeszetes.setDocuments(documents);
				} else if (rsUgyfel.getInt("type") == 2) {
					jogi = new Jogi();
					jogi.setId(rsUgyfel.getLong("id"));
					jogi.setCustomer(rsUgyfel.getBoolean("customer"));
					jogi.setLastIdentified(new Date(rsUgyfel.getDate(
							"last_identified").getTime()));
					jogi.setName(rsUgyfel.getString("name"));
					jogi.setShortName(rsUgyfel.getString("short_name"));
					jogi.setEstablishmentDate(new Date(rsUgyfel.getDate(
							"establishment_date").getTime()));
					jogi.setDocuments(documents);
				}

				break;
			}

		} catch (SQLException e) {
			String errorMessage = "Unable to query ugyfel for id [" + id + "]!";
			throw new UgyfelDaoException(errorMessage, e.getCause());
		} finally {
			closeResultSet(rsUgyfel);
			closePreparedStatement(psSelectUgyfel);
			closeResultSet(rsDok);
			closePreparedStatement(psSelectDok);
			closeConnection(connection);
		}
		if (termeszetes != null) {
			return termeszetes;
		}
		if (jogi != null) {
			return jogi;
		}
		return null;
	}

	public List<Dokumentum> getDocuments(java.util.Date issueDate)
			throws UgyfelDaoException {
		Connection connection = null;
		PreparedStatement psSelect = null;
		ResultSet rs = null;
		List<Dokumentum> documents = new ArrayList<>();
		try {
			connection = ConnectionFactory.getPostgreConncection();

			psSelect = connection
					.prepareStatement(SQL_SELECT_DOKUMENTUM_ISSUE_DATE);
			psSelect.setDate(1, new Date(issueDate.getTime()));
			rs = psSelect.executeQuery();

			while (rs.next()) {
				Dokumentum dokumentum = new Dokumentum();
				dokumentum.setNumber(rs.getString("dok_number"));
				dokumentum.setIssueDate(new Date(rs.getDate("issue_date")
						.getTime()));
				Type type = new Type();
				type.setCode(rs.getString("code"));
				type.setUnique(rs.getBoolean("dok_unique"));
				dokumentum.setType(type);
				documents.add(dokumentum);
			}

		} catch (SQLException e) {
			String errorMessage = "Unable to query documents!";
			throw new UgyfelDaoException(errorMessage, e.getCause());
		} finally {
			closeResultSet(rs);
			closePreparedStatement(psSelect);
			closeConnection(connection);
		}
		return documents;
	}

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new UgyfelDaoException("Unable to close connection!");
			}
		}
	}

	private void closePreparedStatement(PreparedStatement psSelect) {
		if (psSelect != null) {
			try {
				psSelect.close();
			} catch (SQLException e) {
				throw new UgyfelDaoException(
						"Unable to close prepareStatement!");
			}
		}
	}

	private void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new UgyfelDaoException("Unable to close resultSet!");
			}
		}
	}
}

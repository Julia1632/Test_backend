package com.example.User.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 *
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
  name = "fieldsApi",
  version = "v1",
  resource = "fields",
  namespace = @ApiNamespace(
    ownerDomain = "backend.myapplication.User.example.com",
    ownerName = "backend.myapplication.User.example.com",
    packagePath=""
  )
)
public class FieldsEndpoint {

  private static final Logger logger = Logger.getLogger(FieldsEndpoint.class.getName());

  private static final int DEFAULT_LIST_LIMIT = 20;

  static {
    // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
    ObjectifyService.register(Fields.class);
  }

  /**
   * Returns the {@link Fields} with the corresponding ID.
   *
   * @param id the ID of the entity to be retrieved
   * @return the entity with the corresponding ID
   * @throws NotFoundException if there is no {@code Fields} with the provided ID.
   */
  @ApiMethod(
    name = "get",
    path = "fields/{id}",
    httpMethod = ApiMethod.HttpMethod.GET)
  public Fields get(@Named("id") int id) throws NotFoundException {
    logger.info("Getting Fields with ID: " + id);
    Fields fields = ofy().load().type(Fields.class).id(id).now();
    if (fields == null) {
      throw new NotFoundException("Could not find Fields with ID: " + id);
    }
    return fields;
  }

  /**
   * Inserts a new {@code Fields}.
   */
  @ApiMethod(
    name = "insert",
    path = "fields",
    httpMethod = ApiMethod.HttpMethod.POST)
  public Fields insert(Fields fields) {
    // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
    // You should validate that fields.id has not been set. If the ID type is not supported by the
    // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
    //
    // If your client provides the ID then you should probably use PUT instead.
    ofy().save().entity(fields).now();
    logger.info("Created Fields.");

    return ofy().load().entity(fields).now();
  }

  /**
   * Updates an existing {@code Fields}.
   *
   * @param id the ID of the entity to be updated
   * @param fields the desired state of the entity
   * @return the updated version of the entity
   * @throws NotFoundException if the {@code id} does not correspond to an existing
   *  {@code Fields}
   */
  @ApiMethod(
    name = "update",
    path = "fields/{id}",
    httpMethod = ApiMethod.HttpMethod.PUT)
  public Fields update(@Named("id") int id, Fields fields) throws NotFoundException {
    // TODO: You should validate your ID parameter against your resource's ID here.
    checkExists(id);
    ofy().save().entity(fields).now();
    logger.info("Updated Fields: " + fields);
    return ofy().load().entity(fields).now();
  }

  /**
   * Deletes the specified {@code Fields}.
   *
   * @param id the ID of the entity to delete
   * @throws NotFoundException if the {@code id} does not correspond to an existing
   *  {@code Fields}
   */
  @ApiMethod(
    name = "remove",
    path = "fields/{id}",
    httpMethod = ApiMethod.HttpMethod.DELETE)
  public void remove(@Named("id") int id) throws NotFoundException {
    checkExists(id);
    ofy().delete().type(Fields.class).id(id).now();
    logger.info("Deleted Fields with ID: " + id);
  }

  /**
   * List all entities.
   *
   * @param cursor used for pagination to determine which page to return
   * @param limit the maximum number of entries to return
   * @return a response that encapsulates the result list and the next page token/cursor
   */
  @ApiMethod(
    name = "list",
    path = "fields",
    httpMethod = ApiMethod.HttpMethod.GET)
  public CollectionResponse<Fields> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
    limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
    Query<Fields> query = ofy().load().type(Fields.class).limit(limit);
    if (cursor != null) {
      query = query.startAt(Cursor.fromWebSafeString(cursor));
    }
    QueryResultIterator<Fields> queryIterator = query.iterator();
    List<Fields> fieldsList = new ArrayList<Fields>(limit);
    while (queryIterator.hasNext()) {
      fieldsList.add(queryIterator.next());
    }
    return CollectionResponse.<Fields>builder().setItems(fieldsList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
  }

  private void checkExists(int id) throws NotFoundException {
    try {
      ofy().load().type(Fields.class).id(id).safe();
    } catch (com.googlecode.objectify.NotFoundException e) {
      throw new NotFoundException("Could not find Fields with ID: " + id);
    }
  }}